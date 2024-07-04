package com.rehe.auth.admin.service;

import com.rehe.auth.admin.entity.AuthUser;
import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.mapper.AuthUserMapper;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Service
@RequiredArgsConstructor
public class AuthUserService {
    private final AuthUserMapper authUserMapper;
    private final AuthUserMapstruct authUserMapstruct;
    public Optional<AuthUser> findByUsername(String username){
        User user = authUserMapper.findByUsername(username);
        return Optional.ofNullable(authUserMapstruct.toVo(user));
    }

    public AuthUser findByPhone(String phone){
        User user = authUserMapper.findByPhone(phone);
        return authUserMapstruct.toVo(user);
    }

    public AuthUser findByOpenId(String openId){
        User user = authUserMapper.findByOpenId(openId);
        return authUserMapstruct.toVo(user);
    }
}
