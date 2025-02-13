package com.rehe.biz.core.modules.logging.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.biz.core.common.dto.PageParamDto;
import com.rehe.biz.core.modules.logging.dto.OperationLogDto;
import com.rehe.biz.core.modules.logging.dto.reqeust.OperationLogCreateDto;
import com.rehe.biz.core.modules.logging.dto.reqeust.OperationLogQueryDto;
import com.rehe.biz.core.modules.logging.entity.OperationLog;
import com.rehe.biz.core.modules.logging.mapper.OperationLogMapper;
import com.rehe.biz.core.modules.logging.mapstruct.OperationLogMapstruct;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OperationLogService{

    private final OperationLogMapper operationLogMapper;

    @Async
    @Transactional(rollbackFor = Exception.class)
    public void createOperationLog(OperationLogCreateDto operationLogCreateDto) {
        OperationLog operationLog = OperationLogMapstruct.INSTANCE.toEntity(operationLogCreateDto);
        operationLog.setCreateTime(LocalDateTime.now());
        operationLogMapper.insertSelective(operationLog);
    }

    public Page<OperationLogDto> queryOperationLog(OperationLogQueryDto operationLogQueryDto, PageParamDto pageParamDto){
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<OperationLog> operationLogList = operationLogMapper.selectAll(operationLogQueryDto);
        return Page.of(new PageInfo<>(operationLogList), OperationLogMapstruct.INSTANCE.toDto(operationLogList));
    }

    public OperationLogDto getOperationLogById(Long id) {
        return OperationLogMapstruct.INSTANCE.toDto(getById(id));
    }


    /** -----------------------私有方法------------------------ */

    private OperationLog getById(Long id) {
        return Optional.ofNullable(operationLogMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new BusinessException("日志不存在"));
    }
}
