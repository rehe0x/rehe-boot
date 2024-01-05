package com.rehe.controller.admin.security.provider.mobile;

import com.rehe.controller.admin.security.provider.openid.OpenIdAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
public class MobileAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("手机登录");
        return MobileAuthenticationToken.unauthenticated("sdf","sd");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
