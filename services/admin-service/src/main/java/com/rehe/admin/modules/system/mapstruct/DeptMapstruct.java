package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.admin.modules.system.dto.DeptDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptUpdateDto;
import com.rehe.admin.modules.system.dto.response.DeptResponseDto;
import com.rehe.admin.modules.system.entity.Dept;
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
public interface DeptMapstruct extends MapstructDtoBaseMapper<DeptDto, Dept> {
    DeptMapstruct INSTANCE = Mappers.getMapper(DeptMapstruct.class);


    DeptResponseDto toDeptResponseDto(DeptDto userDto);

    List<DeptResponseDto> toDeptResponseDto(List<DeptDto> deptDtoList);

    Dept toEntity(DeptCreateDto dto);

    Dept toEntity(DeptUpdateDto dto);

}
