package com.rehe.auth.admin.service;

import com.rehe.auth.admin.dto.AuthUserDto;
import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUserDto authUserDto =  authUserService.findByUsername(username).orElseThrow(() -> new BadCredentialsException(""));
        JwtUserDto jwtUserDto = AuthUserMapstruct.INSTANCE.toDto(authUserDto);
        jwtUserDto.setAuthorities(authUserService.getUserAuthorities(null, jwtUserDto.getUserId()));
        return jwtUserDto;
    }
    @Override
    public UserDetails loadUserByPhone(String phone) throws UsernameNotFoundException {
        AuthUserDto authUserDto =  authUserService.findByPhone(phone).orElseThrow(() -> new BadCredentialsException(""));
        JwtUserDto jwtUserDto = AuthUserMapstruct.INSTANCE.toDto(authUserDto);
        return jwtUserDto;
    }

    @Override
    public UserDetails loadUserByOpenId(String openId) throws UsernameNotFoundException {
        AuthUserDto authUserDto =  authUserService.findByOpenId(openId).orElseThrow(() -> new BadCredentialsException(""));
        JwtUserDto jwtUserDto = AuthUserMapstruct.INSTANCE.toDto(authUserDto);
        return jwtUserDto;
    }

}
