package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.admin.modules.system.dto.RoleDto;
import com.rehe.admin.modules.system.dto.reqeust.RoleCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.RoleUpdateDto;
import com.rehe.admin.modules.system.entity.Role;
import com.rehe.admin.modules.system.dto.response.RoleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapstruct extends MapstructDtoBaseMapper<RoleDto, Role> {
    RoleMapstruct INSTANCE = Mappers.getMapper(RoleMapstruct.class);

    Page<RoleResponseDto> toRoleResponseDto(Page<RoleDto> page);

    RoleResponseDto toRoleResponseDto(RoleDto roleDto);

    List<RoleResponseDto> toRoleResponseDto(List<RoleDto> roleDtoList);

    Role toEntity(RoleCreateDto dto);

    Role toEntity(RoleUpdateDto dto);
}
