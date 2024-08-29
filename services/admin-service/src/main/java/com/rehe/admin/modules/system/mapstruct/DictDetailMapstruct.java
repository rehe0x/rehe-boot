package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.admin.modules.system.dto.DeptDto;
import com.rehe.admin.modules.system.dto.DictDetailDto;
import com.rehe.admin.modules.system.dto.DictDto;
import com.rehe.admin.modules.system.dto.reqeust.DictCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictDetailCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictDetailUpdateDto;
import com.rehe.admin.modules.system.dto.reqeust.DictUpdateDto;
import com.rehe.admin.modules.system.dto.response.DeptResponseDto;
import com.rehe.admin.modules.system.dto.response.DictDetailResponseDto;
import com.rehe.admin.modules.system.entity.Dict;
import com.rehe.admin.modules.system.entity.DictDetail;
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
public interface DictDetailMapstruct extends MapstructDtoBaseMapper<DictDetailDto, DictDetail> {
    DictDetailMapstruct INSTANCE = Mappers.getMapper(DictDetailMapstruct.class);


    DictDetailResponseDto toDictDetailResponseDto(DictDetailDto dictDetailDto);

    List<DictDetailResponseDto> toDeptResponseDto(List<DictDetailDto> dictDetailDtoList);

    DictDetail toEntity(DictDetailCreateDto dto);

    DictDetail toEntity(DictDetailUpdateDto dto);

}
