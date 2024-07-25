package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import com.rehe.modules.admin.system.dto.MenuDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuUpdateDto;
import com.rehe.modules.admin.system.dto.response.RoleResponseDto;
import com.rehe.modules.admin.system.entity.Menu;
import com.rehe.modules.admin.system.dto.response.MenuResponseDto;
import com.rehe.modules.admin.system.entity.Role;
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
public interface MenuMapstruct extends MapstructVoBaseMapper<MenuDto, Menu> {
    MenuMapstruct INSTANCE = Mappers.getMapper(MenuMapstruct.class);

    MenuResponseDto toMenuResponseDto(MenuDto menuDto);

    List<MenuResponseDto> toMenuResponseDto(List<MenuDto> menuDtoList);


    Menu toEntity(MenuCreateDto dto);

    Menu toEntity(MenuUpdateDto dto);

}
