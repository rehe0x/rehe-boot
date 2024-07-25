package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.system.dto.DeptDto;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.DeptCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.DeptUpdateDto;
import com.rehe.modules.admin.system.dto.response.DeptResponseDto;
import com.rehe.modules.admin.system.dto.response.UserResponseDto;
import com.rehe.modules.admin.system.entity.Dept;
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
public interface DeptMapstruct extends MapstructVoBaseMapper<DeptDto, Dept> {
    DeptMapstruct INSTANCE = Mappers.getMapper(DeptMapstruct.class);


    DeptResponseDto toDeptResponseDto(DeptDto userDto);

    List<DeptResponseDto> toDeptResponseDto(List<DeptDto> deptDtoList);

    Dept toEntity(DeptCreateDto dto);

    Dept toEntity(DeptUpdateDto dto);

}
