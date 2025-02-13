package com.rehe.scheduled.modules.task.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.biz.core.common.dto.PageParamDto;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.log.OperationLog;
import com.rehe.common.result.Page;
import com.rehe.common.uuid.SnowflakeIdGenerator;
import com.rehe.scheduled.manager.DynamicScheduledTaskManager;
import com.rehe.scheduled.modules.task.dto.ScheduledTaskDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskCreateDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskQueryDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskUpdateDto;
import com.rehe.scheduled.modules.task.entity.ScheduledTask;
import com.rehe.scheduled.modules.task.mapstruct.ScheduledTaskMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import com.rehe.scheduled.modules.task.mapper.ScheduledTaskMapper;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduledTaskService{

    private final ScheduledTaskMapper scheduledTaskMapper;

    private final SnowflakeIdGenerator snowflakeIdGenerator;

    @OperationLog(value = "添加定时任务")
    public void createScheduledTask(ScheduledTaskCreateDto scheduledTaskCreateDto) {
        ScheduledTask scheduledTask = ScheduledTaskMapstruct.INSTANCE.toEntity(scheduledTaskCreateDto);
        validateScheduledTask(scheduledTask,null);
        scheduledTask.setTaskId(snowflakeIdGenerator.nextId("TASK"));
        scheduledTask.setCreateTime(LocalDateTime.now());
        scheduledTaskMapper.insertSelective(scheduledTask);
    }

    @OperationLog(value = "修改定时任务")
    public void updateScheduledTask(ScheduledTaskUpdateDto scheduledTaskUpdateDto) {
        ScheduledTask scheduledTask = getById(scheduledTaskUpdateDto.getId());
        ScheduledTask entity = ScheduledTaskMapstruct.INSTANCE.toEntity(scheduledTaskUpdateDto);
        validateScheduledTask(entity,scheduledTask);
        entity.setUpdateTime(LocalDateTime.now());
        scheduledTaskMapper.updateByPrimaryKeySelective(entity);
        if(scheduledTask.getStatus() == 1){
            DynamicScheduledTaskManager.getInstance().scheduleTask(ScheduledTaskMapstruct.INSTANCE.toDto(getById(scheduledTaskUpdateDto.getId())));
        }
    }

    @OperationLog(value = "删除定时任务")
    public void deleteScheduledTask(Long id){
        ScheduledTask scheduledTask = getById(id);
        scheduledTask.setDeleted(true);
        scheduledTask.setUpdateTime(LocalDateTime.now());
        scheduledTaskMapper.updateByPrimaryKeySelective(scheduledTask);
        DynamicScheduledTaskManager.getInstance().stopTask(scheduledTask.getTaskId());
    }

    @OperationLog(value = "暂停/启动任务")
    public void stopStartScheduledTask(Long id,Short status){
        ScheduledTask scheduledTask = getById(id);
        if(!scheduledTask.getStatus().equals(status)){
            scheduledTask.setStatus(status);
            scheduledTask.setUpdateTime(LocalDateTime.now());
            scheduledTaskMapper.updateByPrimaryKeySelective(scheduledTask);
            if(status == 1){
                DynamicScheduledTaskManager.getInstance().scheduleTask(ScheduledTaskMapstruct.INSTANCE.toDto(scheduledTask));
            } else {
                DynamicScheduledTaskManager.getInstance().stopTask(scheduledTask.getTaskId());
            }
        }
    }

    @OperationLog(value = "立即执行任务")
    public void executeTask(Long id){
        ScheduledTask scheduledTask = getById(id);
        DynamicScheduledTaskManager.getInstance().executeTask(scheduledTask.getTaskId());
    }

    @OperationLog(value = "重新加载所有任务")
    public void reloadTask(){
        DynamicScheduledTaskManager.getInstance().reloadTask();
    }

    public Page<ScheduledTaskDto> queryScheduledTask(ScheduledTaskQueryDto scheduledTaskQueryDto, PageParamDto pageParamDto){
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<ScheduledTask> scheduledTaskList = scheduledTaskMapper.selectList(scheduledTaskQueryDto);
        return Page.of(new PageInfo<>(scheduledTaskList), ScheduledTaskMapstruct.INSTANCE.toDto(scheduledTaskList));
    }


    public ScheduledTaskDto getScheduledTaskById(Long id){
        ScheduledTask scheduledTask = getById(id);
        return ScheduledTaskMapstruct.INSTANCE.toDto(scheduledTask);
    }

    public List<ScheduledTaskDto> findTaskByStatus(Integer status){
        return ScheduledTaskMapstruct.INSTANCE.toDto(scheduledTaskMapper.selectByStatus(status));
    }

    public ScheduledTaskDto findTaskByTaskId(String taskId){
        return ScheduledTaskMapstruct.INSTANCE.toDto(scheduledTaskMapper.selectByTaskId(taskId));
    }


    private ScheduledTask getById(Long id){
        return Optional.ofNullable(
                scheduledTaskMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new BusinessException("任务不存在"));
    }

    private void validateScheduledTask(ScheduledTask entity,ScheduledTask updateEntity) {
        ScheduledTask scheduledTask = scheduledTaskMapper.selectByTaskName(entity.getTaskName());
        if (scheduledTask != null) {
            if (updateEntity == null || !updateEntity.getTaskName().equals(entity.getTaskName())) {
                throw new BusinessException("任务名已存在！");
            }
        }
        if(entity.getTaskMode() == 1){
            if(!CronExpression.isValidExpression(entity.getExpression())){
                throw new BusinessException("cron表达式错误！");
            }
        } else {
            try {
                Long.parseLong(entity.getExpression()); // 尝试将字符串转换为 long 类型
            } catch (NumberFormatException e) {
                throw new BusinessException("间隔时间表达式错误！");
            }
        }
    }

}
