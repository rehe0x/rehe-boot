package com.rehe.auth.admin.provider.openid;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

/**
 * @author xiech
 * @description 微信登录token
 * @date 2024/1/3
 */
public class OpenIdAuthenticationToken extends AbstractAuthenticationToken {
    @Serial
    private static final long serialVersionUID = -4990177973814524189L;

    private final Object openId;


    public OpenIdAuthenticationToken(Object openId) {
        super(null);
        this.openId = openId;
        this.setAuthenticated(false);
    }

    public OpenIdAuthenticationToken(Object openId ,Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.openId = openId;
        super.setAuthenticated(true);
    }

    public static OpenIdAuthenticationToken unauthenticated(Object openId) {
        return new OpenIdAuthenticationToken(openId);
    }

    public static OpenIdAuthenticationToken authenticated(Object openId,  Collection<? extends GrantedAuthority> authorities) {
        return new OpenIdAuthenticationToken(openId, authorities);
    }

    public Object getCredentials() {
        return null;
    }

    public Object getPrincipal() {
        return this.openId;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
    }
}
