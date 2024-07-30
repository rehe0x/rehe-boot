package com.rehe.auth.admin.service;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * @author xiech
 * @description
 * @date 2024/1/9
 */
public interface CustomUserDetailsService extends UserDetailsService {
    UserDetails loadUserByPhone(String phone)throws UsernameNotFoundException;
    UserDetails loadUserByOpenId(String openId)throws UsernameNotFoundException;
}
