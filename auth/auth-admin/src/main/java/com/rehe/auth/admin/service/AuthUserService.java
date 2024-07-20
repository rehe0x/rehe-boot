package com.rehe.auth.admin.service;

import com.rehe.auth.admin.entity.AuthUser;
import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.mapper.AuthUserMapper;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import com.rehe.auth.admin.vo.AuthMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
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
        User user = authUserMapper.selectByUsername(username);
        return Optional.ofNullable(authUserMapstruct.toVo(user));
    }

    public AuthUser findByPhone(String phone){
        User user = authUserMapper.selectByPhone(phone);
        return authUserMapstruct.toVo(user);
    }

    public AuthUser findByOpenId(String openId){
        User user = authUserMapper.selectByOpenId(openId);
        return authUserMapstruct.toVo(user);
    }

    public List<AuthMenuVo> getUserMenus(Integer platformId, Long userId){
        List<AuthMenuVo> menuVoList = authUserMapper.selectMenuByUser(platformId, userId);
        return menuVoList;
    }


}
