package com.rehe.modules.admin.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.MenuDto;
import com.rehe.modules.admin.system.dto.RoleDto;
import com.rehe.modules.admin.system.dto.reqeust.RoleCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.RoleMenuBindDto;
import com.rehe.modules.admin.system.dto.reqeust.RoleUpdateDto;
import com.rehe.modules.admin.system.mapstruct.RoleMapstruct;
import com.rehe.modules.admin.system.dto.response.MenuResponseDto;
import com.rehe.modules.admin.system.dto.response.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.rehe.modules.admin.system.entity.Role;
import com.rehe.modules.admin.system.mapper.RoleMapper;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @description
 * @author rehe
 * @date 2024/7/23
 */

@RequiredArgsConstructor
@Service
public class RoleService{

    private final  RoleMapper roleMapper;

    private final MenuService menuService;

    public void createRole(RoleCreateDto roleCreateDto) {
        Role entity = RoleMapstruct.INSTANCE.toEntity(roleCreateDto);
        validateUniqueRole(entity.getName() ,null);
        entity.setCreateTime(LocalDateTime.now());
        roleMapper.insertSelective(entity);
    }

    public void updateRole(RoleUpdateDto roleUpdateDto) {
        Role role = getById(roleUpdateDto.getId());
        Role entity = RoleMapstruct.INSTANCE.toEntity(roleUpdateDto);
        entity.setId(role.getId());
        entity.setUpdateTime(LocalDateTime.now());
        validateUniqueRole(entity.getName() ,role.getName());
        roleMapper.updateByPrimaryKeySelective(entity);
    }

    public void deleteRole(Long roleId) {
        Role role = getById(roleId);
        int roleUserCount = roleMapper.selectRoleUserCount(role.getId());
        if(roleUserCount > 0) {
            throw new BusinessException("当前角色有用户绑定，请先取消");
        }
        roleMapper.deleteByPrimaryKey(role.getId());
        roleMapper.deleteRoleMenuByRoleId(role.getId());
    }

    public void bindRoleMenu(RoleMenuBindDto roleMenuBindDto) {
        if(!validateRoleMenuBind(roleMenuBindDto)){
            return;
        };
        roleMapper.insertRoleMenu(roleMenuBindDto.getId(), roleMenuBindDto.getMenuIds());
    }


    public List<RoleDto> queryRole() {
        List<Role> roleList = roleMapper.selectList();
        return RoleMapstruct.INSTANCE.toDto(roleList);
    }

    public RoleDto getRoleById(Long roleId) {
        return RoleMapstruct.INSTANCE.toDto(roleMapper.selectByPrimaryKey(roleId));
    }

    private Role getById(Long roleId) {
        return Optional.ofNullable(roleMapper.selectByPrimaryKey(roleId))
                .orElseThrow(()-> new BusinessException("角色不存在"));
    }

    private void validateUniqueRole(String roleName,String oldRoleName){
        Role role = roleMapper.selectByName(roleName);
        if(role == null){
            return;
        }
        if(StringUtils.hasText(oldRoleName) && oldRoleName.equals(role.getName())){
            return;
        }
        throw new BusinessException("角色名称已存在");
    }

    private boolean validateRoleMenuBind(RoleMenuBindDto roleMenuBindDto) {
        if(CollectionUtils.isEmpty(roleMenuBindDto.getMenuIds())) {
            return false;
        }
        Role role = getById(roleMenuBindDto.getId());
        List<MenuDto> menuDtoList = menuService.getMenuAll().orElseThrow(() -> new BusinessException("菜单数据异常"));
        Set<Long> menuIds = menuDtoList
                .stream().map(MenuDto::getId).collect(Collectors.toSet());
        if(!menuIds.containsAll(roleMenuBindDto.getMenuIds())){
            throw new BusinessException("有菜单不存在");
        }
        return true;
    }
}
