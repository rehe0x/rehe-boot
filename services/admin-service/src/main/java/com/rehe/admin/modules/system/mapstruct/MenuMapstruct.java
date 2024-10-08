package com.rehe.admin.modules.system.mapstruct;

import com.rehe.common.mapstruct.MapstructDtoBaseMapper;
import com.rehe.admin.modules.system.dto.MenuDto;
import com.rehe.admin.modules.system.dto.reqeust.MenuCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.MenuUpdateDto;
import com.rehe.admin.modules.system.entity.Menu;
import com.rehe.admin.modules.system.dto.response.MenuResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapstruct extends MapstructDtoBaseMapper<MenuDto, Menu> {
    MenuMapstruct INSTANCE = Mappers.getMapper(MenuMapstruct.class);

    MenuResponseDto toMenuResponseDto(MenuDto menuDto);

    List<MenuResponseDto> toMenuResponseDto(List<MenuDto> menuDtoList);


    Menu toEntity(MenuCreateDto dto);

    Menu toEntity(MenuUpdateDto dto);

}
