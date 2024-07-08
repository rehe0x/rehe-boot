package com.rehe.modules.admin.system.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Page;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.UserAddDto;
import com.rehe.modules.admin.system.dto.UserQueryDto;
import com.rehe.modules.admin.system.dto.UserUpdateDto;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.mapstruct.UserMapstruct;
import com.rehe.modules.admin.system.vo.UserVo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.rehe.modules.admin.system.mapper.UserMapper;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author rehe
 */
@Service
@RequiredArgsConstructor
public class UserService{
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    
    public void addUser(UserAddDto userAddDto) {
        User entity = UserMapstruct.INSTANCE.toEntity(userAddDto);
        validateUser(entity, null);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setCreateTime(LocalDateTime.now());
        userMapper.insertSelective(entity);
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = getById(userUpdateDto.getId());
        User entity = UserMapstruct.INSTANCE.toEntity(userUpdateDto);
        validateUser(entity, user);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
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

    public Page<UserVo> queryUsers(UserQueryDto userQueryDto, PageParamDto pageParamDto){
        PageHelper.startPage(pageParamDto.getPageNum(), pageParamDto.getPageSize());
        List<User> userList = userMapper.selectAll();
        return Page.of(new PageInfo<>(userList), UserMapstruct.INSTANCE.toVo(userList));
    }

    public UserVo getUserById(Long id) {
        User user = getById(id);
        return UserMapstruct.INSTANCE.toVo(user);
    }


    /** -----------------------私有方法------------------------ */

    private User getById(Long id) {
        User user = userMapper.selectByPrimaryKey(id);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }
        return user;
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
