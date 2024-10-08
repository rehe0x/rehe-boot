package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.common.result.Page;
import com.rehe.admin.modules.system.dto.DeptDto;
import com.rehe.admin.modules.system.dto.DictDto;
import com.rehe.admin.modules.system.dto.UserDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DeptUpdateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictUpdateDto;
import com.rehe.admin.modules.system.dto.response.DeptResponseDto;
import com.rehe.admin.modules.system.dto.response.DictResponseDto;
import com.rehe.admin.modules.system.dto.response.UserResponseDto;
import com.rehe.admin.modules.system.entity.Dept;
import com.rehe.admin.modules.system.entity.Dict;
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
public interface DictMapstruct extends MapstructDtoBaseMapper<DictDto, Dict> {
    DictMapstruct INSTANCE = Mappers.getMapper(DictMapstruct.class);

    Page<DictResponseDto> toDictResponseDto(Page<DictDto> page);

    DictResponseDto toDictResponseDto(DictDto dictDto);

    List<DictResponseDto> toDictResponseDto(List<DictDto> dictDtoList);

    Dict toEntity(DictCreateDto dto);

    Dict toEntity(DictUpdateDto dto);

}
