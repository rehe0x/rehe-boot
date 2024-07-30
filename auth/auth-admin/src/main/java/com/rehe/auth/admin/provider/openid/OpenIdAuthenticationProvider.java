package com.rehe.auth.admin.provider.openid;

import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.service.CustomUserDetailsService;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author xiech
 * @description
 * @date 2024/1/3
 */
@Setter
public class OpenIdAuthenticationProvider implements AuthenticationProvider {

    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        OpenIdAuthenticationToken openIdAuthenticationToken = (OpenIdAuthenticationToken)authentication;
        JwtUserDto authUser = (JwtUserDto) customUserDetailsService.loadUserByOpenId(openIdAuthenticationToken.getPrincipal().toString());
        // 不存在账号密码错误 或 创建账号
        if (authUser == null) {
            throw new BadCredentialsException("");
        }
        return OpenIdAuthenticationToken.unauthenticated(openIdAuthenticationToken.getPrincipal());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
