package com.rehe.auth.admin.service;


import com.rehe.auth.admin.entity.AuthUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xiech
 * @description
 * @date 2024/1/9
 */
public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails findByPhone(String phone)throws UsernameNotFoundException;

    UserDetails findByOpenId(String openId)throws UsernameNotFoundException;
}
