package com.rehe.scheduled.modules.task.mapper;

import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskQueryDto;
import com.rehe.scheduled.modules.task.entity.ScheduledTask;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ScheduledTaskMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ScheduledTask record);

    int insertSelective(ScheduledTask record);

    ScheduledTask selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ScheduledTask record);

    int updateByPrimaryKey(ScheduledTask record);

    List<ScheduledTask> selectList(ScheduledTaskQueryDto scheduledTaskQueryDto);

    List<ScheduledTask> selectAll();

    List<ScheduledTask> selectByStatus(Integer status);

    ScheduledTask selectByTaskId(String taskId);

    ScheduledTask selectByTaskName(String taskName);

}