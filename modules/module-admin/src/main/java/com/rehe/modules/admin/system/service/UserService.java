package com.rehe.modules.admin.system.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.UserCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.UserQueryDto;
import com.rehe.modules.admin.system.dto.reqeust.UserUpdateDto;
import com.rehe.modules.admin.system.dto.response.UserResponseDto;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.mapstruct.UserMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rehe.modules.admin.system.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
    
    public void createUser(UserCreateDto userCreateDto) {
        User entity = UserMapstruct.INSTANCE.toEntity(userCreateDto);
        validateUser(entity, null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreateTime(LocalDateTime.now());
        userMapper.insertSelective(entity);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = getById(userUpdateDto.getId());
        User entity = UserMapstruct.INSTANCE.toEntity(userUpdateDto);

        validateUser(entity, user);
        if(!user.getPassword().equals(entity.getPassword())){
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        }
        entity.setUpdateTime(LocalDateTime.now());
        userMapper.updateByPrimaryKeySelective(entity);
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
        User user = getById(id);
        return UserMapstruct.INSTANCE.toDto(user);
    }

    public List<UserDto> findUserByDeptIds(List<Long> deptIds) {
        List<User> userList = userMapper.selectByDeptIds(deptIds);
        return UserMapstruct.INSTANCE.toDto(userList);
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

}
