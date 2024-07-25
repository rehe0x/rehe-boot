package com.rehe.modules.admin.system.service;
import java.time.LocalDateTime;

import com.rehe.common.exception.BusinessException;
import com.rehe.modules.admin.system.dto.MenuDto;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuQueryDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuUpdateDto;
import com.rehe.modules.admin.system.dto.response.MenuResponseDto;
import com.rehe.modules.admin.system.mapstruct.MenuMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.rehe.modules.admin.system.entity.Menu;
import com.rehe.modules.admin.system.mapper.MenuMapper;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description
 * @author rehe
 * @date 2024/6/26
 */
@RequiredArgsConstructor
@Service
public class MenuService{

    private final MenuMapper menuMapper;

    /**
     * 新增菜单
     */
    public void createMenu(MenuCreateDto menuCreateDto){
        Menu entity = MenuMapstruct.INSTANCE.toEntity(menuCreateDto);
        entity.setCreateTime(LocalDateTime.now());

        Menu parentMenu = validateParentMenu(entity.getParentId());

        // 默认路由特殊处理
        if (menuCreateDto.getRouteDefault()) {
            handleRouteDefault(parentMenu, entity);
        }

        // 菜单和权限需要验证权限标识唯一性
        if(!menuCreateDto.getMenuType().equals(0)){
            validateUniquePermission(entity.getPlatformId(),entity.getPermission(),null);
        }

        // 目录和菜单需要验证路由路径唯一性
        if(!menuCreateDto.getMenuType().equals(2)){
            validateUniqueRoute(entity.getPlatformId(),entity.getParentId(),entity.getRoutePath(),null);
        }
        menuMapper.insertSelective(entity);
    }


    /**
     * 修改菜单
     */
    public void updateMenu(MenuUpdateDto menuUpdateDto){
        Menu menu = getById(menuUpdateDto.getId());
        Menu entity = MenuMapstruct.INSTANCE.toEntity(menuUpdateDto);
        entity.setParentId(menu.getParentId());
        entity.setMenuType(menu.getMenuType());
        entity.setUpdateTime(LocalDateTime.now());
        entity.setPlatformId(menu.getPlatformId());
        Menu parentMenu = validateParentMenu(entity.getParentId());

        if (menuUpdateDto.getRouteDefault()) {
            handleRouteDefault(parentMenu, entity);
        }

        if(!entity.getMenuType().equals(0)){
            validateUniquePermission(entity.getPlatformId(), entity.getPermission(),menu.getPermission());
        }

        if(!entity.getMenuType().equals(2)){
            validateUniqueRoute(entity.getPlatformId(), entity.getParentId(),entity.getRoutePath(),menu.getRoutePath());
        }
        menuMapper.updateByPrimaryKeySelective(entity);
    }

    /**
     * 删除菜单
     */
    public void deleteMenu(Long id){
        List<Menu> menuList = getAll();
        Map<Long, List<Menu>> parentToChildrenMap = menuList.stream()
                .collect(Collectors.groupingBy(Menu::getParentId));
        List<Long> childIds = getMenuChildIds(parentToChildrenMap, id, new ArrayList<>());
        childIds.add(id);
        menuMapper.deleteByPrimaryKeys(childIds);
    }


    /**
     * 菜单列表
     */
    public List<MenuDto> queryMenu(MenuQueryDto menuQueryDto){
        List<Menu> menuList = menuMapper.selectAll(menuQueryDto);
        return MenuMapstruct.INSTANCE.toDto(menuList);
    }

    /**
     * 按ID获取菜单详情 不存在返回异常
     */
    public MenuDto getMenuById(Long id){
        Menu menu = getById(id);
        return MenuMapstruct.INSTANCE.toDto(menu);
    }


    public Optional<List<MenuDto>> getMenuAll(){
        return Optional.of(MenuMapstruct.INSTANCE.toDto(menuMapper.selectAll(null)));
    }


    /** -----------------------私有方法------------------------ */

    // 递归方法
    private List<Long> getMenuChildIds(Map<Long, List<Menu>> parentToChildrenMap, Long parentId, List<Long> childIds) {
        List<Menu> childList = parentToChildrenMap.get(parentId);
        if(childList == null){
            return childIds;
        }
        for (Menu menu : childList) {
            childIds.add(menu.getId());
            getMenuChildIds(parentToChildrenMap, menu.getId(), childIds);
        }
        return childIds;
    }


    private Menu getById(Long id){
        return Optional.ofNullable(menuMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new BusinessException(String.format("该菜单不存在 %s",id)));
    }

    private Optional<Menu> findById(Long id){
        return Optional.ofNullable(menuMapper.selectByPrimaryKey(id));
    }

    private List<Menu> getAll(){
        return menuMapper.selectAll(null);
    }
    /**
     * 验证上级路由
     */
    private Menu validateParentMenu(Long parentId) {
        if(parentId == null || parentId.equals(0L)){
            return null;
        }
        return findById(parentId)
                .orElseThrow(() -> new BusinessException(String.format("上级不存在 %s", parentId)));
    }

    /**
     * 默认路由特殊处理
     */
    private void handleRouteDefault(Menu parentMenu, Menu entity){
        if (!entity.getMenuType().equals(1)) {
            return;
        }
        if (parentMenu == null || parentMenu.getMenuType().equals(1)) {
            entity.setRoutePath("");
        }
    }

    /**
     * 验证路由唯一性
     */
    private void validateUniqueRoute(Integer platformId,Long parentId, String routePath,String oldRoutePath) {
        if (routePath == null) {
            return;
        }
        Menu menu = menuMapper.selectByPidRoute(platformId, parentId, routePath);
        if (menu != null) {
            if (oldRoutePath != null && oldRoutePath.equals(menu.getRoutePath())) {
                return;
            }
            throw new BusinessException(String.format("该路由已存在 %s", routePath));
        }
    }

    /**
     * 验证权限唯一性
     */
    private void validateUniquePermission(Integer platformId, String permission, String oldPermission) {
        if (!StringUtils.hasText(permission)) {
            return;
        }
        Menu menu = menuMapper.selectByPermission(platformId,permission);
        if (menu != null) {
            if (oldPermission != null && oldPermission.equals(menu.getPermission())) {
                return;
            }
            throw new BusinessException(String.format("该权限已存在 %s", permission));
        }
    }

}
