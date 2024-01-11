package com.rehe.auth.admin.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author xiech
 * @description
 * @date 2024/1/9
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService{
    private final AuthUserService authUserService;
    @Override
    public UserDetails findByPhone(String phone) throws UsernameNotFoundException {
        return authUserService.findByPhone(phone);
    }

    @Override
    public UserDetails findByOpenId(String openId) throws UsernameNotFoundException {
        return authUserService.findByOpenId(openId);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return authUserService.findByUsername(username).orElseThrow();
    }
}
