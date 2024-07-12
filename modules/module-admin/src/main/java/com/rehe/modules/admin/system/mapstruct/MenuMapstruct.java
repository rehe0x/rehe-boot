package com.rehe.modules.admin.system.mapstruct;

import com.rehe.common.mapstruct.MapstructVoBaseMapper;
import com.rehe.modules.admin.system.dto.MenuCreateDto;
import com.rehe.modules.admin.system.dto.MenuUpdateDto;
import com.rehe.modules.admin.system.entity.Menu;
import com.rehe.modules.admin.system.vo.MenuVo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Mapper(componentModel = "spring",uses = {},unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuMapstruct extends MapstructVoBaseMapper<MenuVo, Menu> {
    MenuMapstruct INSTANCE = Mappers.getMapper(MenuMapstruct.class);

    Menu toEntity(MenuCreateDto dto);

    Menu toEntity(MenuUpdateDto dto);

    Menu toEntity(Menu entity);

}
