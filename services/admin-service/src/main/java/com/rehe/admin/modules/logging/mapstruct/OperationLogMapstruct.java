package com.rehe.admin.modules.logging.mapstruct;

import com.rehe.admin.modules.logging.dto.OperationLogDto;
import com.rehe.admin.modules.logging.dto.reqeust.OperationLogCreateDto;
import com.rehe.admin.modules.logging.dto.response.OperationLogResonseDto;
import com.rehe.admin.modules.logging.entity.OperationLog;
import com.rehe.admin.modules.system.dto.DeptDto;
import com.rehe.admin.modules.system.dto.UserDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptUpdateDto;
import com.rehe.admin.modules.system.dto.response.DeptResponseDto;
import com.rehe.admin.modules.system.dto.response.UserResponseDto;
import com.rehe.admin.modules.system.entity.Dept;
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
