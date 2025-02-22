package com.rehe.biz.core.modules.logging.mapper;

import com.rehe.biz.core.modules.logging.dto.reqeust.OperationLogQueryDto;
import com.rehe.biz.core.modules.logging.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationLogMapper {
    int deleteByPrimaryKey(Long id);

    int insert(OperationLog record);

    int insertSelective(OperationLog record);

    OperationLog selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(OperationLog record);

    int updateByPrimaryKey(OperationLog record);

    List<OperationLog> selectAll(OperationLogQueryDto operationLogQueryDto);
}