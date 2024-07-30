package com.rehe.modules.admin.system.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.DeptDto;
import com.rehe.modules.admin.system.dto.MenuDto;
import com.rehe.modules.admin.system.dto.RoleDto;
import com.rehe.modules.admin.system.dto.reqeust.*;
import com.rehe.modules.admin.system.mapstruct.RoleMapstruct;
import com.rehe.modules.admin.system.dto.response.MenuResponseDto;
import com.rehe.modules.admin.system.dto.response.RoleResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.rehe.modules.admin.system.entity.Role;
import com.rehe.modules.admin.system.mapper.RoleMapper;
import org.springframework.transaction.annotation.Transactional;
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

@RequiredArgsConstructor(onConstructor_ = {@Lazy})
@Service
public class RoleService{

    private final  RoleMapper roleMapper;

    private final MenuService menuService;


    private final DeptService deptService;

    @Transactional(rollbackFor = Exception.class)
    public void createRole(RoleCreateDto roleCreateDto) {
        Role entity = RoleMapstruct.INSTANCE.toEntity(roleCreateDto);
        entity.setCreateTime(LocalDateTime.now());
        validateUniqueRole(entity.getName() ,null);
        roleMapper.insertSelective(entity);
        if(roleCreateDto.getScope() == 2 && validateRoleDept(roleCreateDto.getDeptIds())){
            roleMapper.insertRoleDept(entity.getId(), roleCreateDto.getDeptIds());
        };
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateRole(RoleUpdateDto roleUpdateDto) {
        Role role = getById(roleUpdateDto.getId());
        validateSuperRole(role);
        Role entity = RoleMapstruct.INSTANCE.toEntity(roleUpdateDto);
        entity.setId(role.getId());
        entity.setUpdateTime(LocalDateTime.now());
        validateUniqueRole(entity.getName() ,role.getName());

        if(role.getScope() == 2 && entity.getScope() != 2){
            roleMapper.deleteRoleDeptByRoleId(entity.getId());
        } else if(entity.getScope() == 2 && validateRoleDept(roleUpdateDto.getDeptIds())){
            roleMapper.deleteRoleDeptByRoleId(entity.getId());
            roleMapper.insertRoleDept(entity.getId(), roleUpdateDto.getDeptIds());
        }

        roleMapper.updateByPrimaryKeySelective(entity);
    }

    public void deleteRole(Long roleId) {
        Role role = getById(roleId);
        validateSuperRole(role);
        int roleUserCount = roleMapper.selectRoleUserCount(role.getId());
        if(roleUserCount > 0) {
            throw new BusinessException("当前角色有用户绑定，请先取消");
        }
        roleMapper.deleteByPrimaryKey(role.getId());
        roleMapper.deleteRoleMenuByRoleId(role.getId());
        roleMapper.deleteRoleDeptByRoleId(role.getId());
    }

    public void bindRoleMenu(RoleMenuBindDto roleMenuBindDto) {
        if(!validateRoleMenuBind(roleMenuBindDto)){
            return;
        };
        roleMapper.deleteRoleMenuByRoleId(roleMenuBindDto.getId());
        roleMapper.insertRoleMenu(roleMenuBindDto.getId(), roleMenuBindDto.getMenuIds());
    }


    public Page<RoleDto> queryRole(RoleQueryDto roleQueryDto, PageParamDto pageParamDto) {
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<Role> roleList = roleMapper.selectList(roleQueryDto);
        return Page.of(new PageInfo<>(roleList),RoleMapstruct.INSTANCE.toDto(roleList));
    }

    public List<RoleDto> queryRole() {
        List<Role> roleList = roleMapper.selectAll();
        return RoleMapstruct.INSTANCE.toDto(roleList);
    }

    public RoleDto getRoleById(Long roleId) {
        RoleDto roleDto = RoleMapstruct.INSTANCE.toDto(getById(roleId));
        if(roleDto.getScope() == 2){
            roleDto.setDeptIds(roleMapper.selectRoleDeptIdsByRoleId(roleId));
        }
        if(roleDto.getLevel() == 0){
            List<MenuDto> menuDtoList = menuService.getMenuAll().orElseThrow(() -> new BusinessException("菜单数据异常"));
            Set<Long> idSet = menuDtoList.stream()
                    .map(MenuDto::getId)
                    .collect(Collectors.toSet());
            roleDto.setMenuIds(idSet);
        } else{
            roleDto.setMenuIds(roleMapper.selectRoleMenuIdsByRoleId(roleId));
        }
        return roleDto;
    }

    public Optional<RoleDto> findRoleById(Long roleId) {
        RoleDto roleDto = RoleMapstruct.INSTANCE.toDto(roleMapper.selectByPrimaryKey(roleId));
        return Optional.ofNullable(roleDto);
    }


    private Role getById(Long roleId) {
        return Optional.ofNullable(roleMapper.selectByPrimaryKey(roleId))
                .orElseThrow(()-> new BusinessException("角色不存在"));
    }

    private void validateSuperRole(Role role){
        if(role.getLevel() == 0){
            throw new BusinessException("超级管理员无法修改/删除");
        }
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

    private boolean validateRoleDept(Set<Long> deptIds) {
        if(CollectionUtils.isEmpty(deptIds)) {
            throw new BusinessException("请选择部门");
        }
        List<DeptDto> deptDtoList = deptService.getDeptAll().orElseThrow(() -> new BusinessException("部门数据异常"));
        Set<Long> ids = deptDtoList
                .stream().map(DeptDto::getId).collect(Collectors.toSet());
        if(!ids.containsAll(deptIds)){
            throw new BusinessException("有部门不存在");
        }
        return true;
    }

    private boolean validateRoleMenuBind(RoleMenuBindDto roleMenuBindDto) {
        if(CollectionUtils.isEmpty(roleMenuBindDto.getMenuIds())) {
            return false;
        }
        Role role = getById(roleMenuBindDto.getId());
        validateSuperRole(role);
        List<MenuDto> menuDtoList = menuService.getMenuAll().orElseThrow(() -> new BusinessException("菜单数据异常"));
        Set<Long> menuIds = menuDtoList
                .stream().map(MenuDto::getId).collect(Collectors.toSet());
        if(!menuIds.containsAll(roleMenuBindDto.getMenuIds())){
            throw new BusinessException("有菜单不存在");
        }
        return true;
    }
}
