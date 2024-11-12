package com.rehe.admin.modules.system.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.log.OperationLog;
import com.rehe.common.result.Page;
import com.rehe.admin.common.dto.PageParamDto;
import com.rehe.admin.modules.system.dto.RoleDto;
import com.rehe.admin.modules.system.dto.UserDto;
import com.rehe.admin.modules.system.dto.reqeust.UserCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.UserQueryDto;
import com.rehe.admin.modules.system.dto.reqeust.UserUpdateDto;
import com.rehe.admin.modules.system.dto.response.UserResponseDto;
import com.rehe.admin.modules.system.entity.User;
import com.rehe.admin.modules.system.mapstruct.UserMapstruct;
import com.rehe.admin.modules.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rehe.admin.modules.system.mapper.UserMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Transactional(rollbackFor = Exception.class)
    public void createUser(UserCreateDto userCreateDto,Long loginUserId) {
        User entity = UserMapstruct.INSTANCE.toEntity(userCreateDto);
        validateUser(entity, null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreateTime(LocalDateTime.now());
        userMapper.insertSelective(entity);
        if(validateUserRole(loginUserId,userCreateDto.getRoleIds() ,null)){
            userMapper.insertUserRole(entity.getId(), userCreateDto.getRoleIds());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @OperationLog(value = "修改用户")
    public void updateUser(UserUpdateDto userUpdateDto,Long loginUserId) {
        User user = getById(userUpdateDto.getId());
        User entity = UserMapstruct.INSTANCE.toEntity(userUpdateDto);
        entity.setId(user.getId());
        validateUser(entity, user);
        if(!user.getPassword().equals(entity.getPassword())){
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        entity.setUpdateTime(LocalDateTime.now());

        Integer roleLevel = userMapper.selectUserRoleMaxLevel(entity.getId());
        if(roleLevel != null && roleLevel == 0){
            userMapper.updateByPrimaryKeySelective(entity);
            return;
        }

        if(validateUserRole(loginUserId, userUpdateDto.getRoleIds(),userMapper.selectUserRoleIds(entity.getId()))){
            userMapper.deleteUserRole(entity.getId());
            userMapper.insertUserRole(entity.getId(), userUpdateDto.getRoleIds());
        } else {
            userMapper.deleteUserRole(entity.getId());
        }
        userMapper.updateByPrimaryKeySelective(entity);
    }

    public void updateUserPlatform(Long id,Integer platformId){
        userMapper.updatePlatformByUserId(id, platformId);
    }
    public void deleteUser(Long id) {
        User user = getById(id);
        User entity = new User();
        entity.setId(user.getId());
        entity.setDeleted(true);
        entity.setUpdateTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(entity);
    }

    public Page<UserDto> queryUser(UserQueryDto userQueryDto, PageParamDto pageParamDto){
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<User> userList = userMapper.selectAll(userQueryDto);
        return Page.of(new PageInfo<>(userList), UserMapstruct.INSTANCE.toDto(userList));
    }

    public UserDto getUserById(Long id) {
        UserDto userDto = UserMapstruct.INSTANCE.toDto(getById(id));
        userDto.setRoleIds(userMapper.selectUserRoleIds(userDto.getId()));
        return userDto;
    }

    public Optional<List<UserDto>> findUserByDeptIds(List<Long> deptIds) {
        List<User> userList = userMapper.selectByDeptIds(deptIds);
        return Optional.ofNullable(UserMapstruct.INSTANCE.toDto(userList));
    }

    /** -----------------------私有方法------------------------ */

    private User getById(Long id) {
        return Optional.ofNullable(userMapper.selectByPrimaryKey(id))
                .orElseThrow(() -> new BusinessException("用户不存在"));
    }

    private User findById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    private User findByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    private void validateUser(User entity,User updateUser) {
        User user = findByUsername(entity.getUsername());
        if (user != null) {
            if (updateUser == null || !updateUser.getUsername().equals(user.getUsername())) {
                throw new BusinessException("用户名已存在！");
            }
        }
    }

    public boolean validateUserRole(Long loginUserId,Set<Long> roleIds,Set<Long> oldRoleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return false;
        }
        Integer roleLevel = userMapper.selectUserRoleMaxLevel(loginUserId);
        if(roleLevel == null){
            throw new BusinessException("登录角色异常,请退出");
        }
        roleIds.forEach(roleId -> {
            RoleDto roleDto = roleService.findRoleById(roleId).orElseThrow(() -> new BusinessException("角色不存在"+roleId));
            if(!CollectionUtils.isEmpty(oldRoleIds) && oldRoleIds.contains(roleId)){
                return;
            }
            if(roleDto.getLevel() <= roleLevel){
                throw new BusinessException("没有该角色权限"+roleId);
            }
        });
        return true;
    }

}
