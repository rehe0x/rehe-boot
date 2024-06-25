package com.rehe.auth.admin.provider.mobile;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.Assert;

import java.io.Serial;
import java.util.Collection;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken {

    @Serial
    private static final long serialVersionUID = 1421546263761198624L;
    private final Object phone;
    private Object code;

    public MobileAuthenticationToken(Object phone, Object code) {
        super(null);
        this.phone = phone;
        this.code = code;
        this.setAuthenticated(false);
    }

    public MobileAuthenticationToken(Object phone, Object code, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.phone = phone;
        this.code = code;
        super.setAuthenticated(true);
    }

    public static MobileAuthenticationToken unauthenticated(Object phone, Object code) {
        return new MobileAuthenticationToken(phone, code);
    }

    public static MobileAuthenticationToken authenticated(Object phone, Object code, Collection<? extends GrantedAuthority> authorities) {
        return new MobileAuthenticationToken(phone, code, authorities);
    }

    public Object getCredentials() {
        return this.code;
    }

    public Object getPrincipal() {
        return this.phone;
    }

    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        Assert.isTrue(!isAuthenticated, "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        super.setAuthenticated(false);
    }

    public void eraseCredentials() {
        super.eraseCredentials();
        this.code = null;
    }
}
