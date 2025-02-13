package com.rehe.biz.core.modules.logging.mapstruct;

import com.rehe.biz.core.modules.logging.dto.OperationLogDto;
import com.rehe.biz.core.modules.logging.dto.reqeust.OperationLogCreateDto;
import com.rehe.biz.core.modules.logging.dto.response.OperationLogResonseDto;
import com.rehe.biz.core.modules.logging.entity.OperationLog;
import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.common.result.Page;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @description
 * @author rehe
 * @date 2024/11/11
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperationLogMapstruct extends MapstructDtoBaseMapper<OperationLogDto, OperationLog> {
    OperationLogMapstruct INSTANCE = Mappers.getMapper(OperationLogMapstruct.class);
    Page<OperationLogResonseDto> toOperationLogResponseDto(Page<OperationLogDto> page);

    OperationLogResonseDto toOperationLogResponseDto(OperationLogDto operationLogDto);

    List<OperationLogResonseDto> toOperationLogResponseDto(List<OperationLogDto> operationLogDtoList);

    OperationLog toEntity(OperationLogCreateDto dto);

}
