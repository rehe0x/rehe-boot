package com.rehe.auth.admin.dto;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Set;

/**
 * @description
 * @author rehe
 * @date 2024/7/29
 */
@Setter
@Getter
public class JwtUserDto implements UserDetails {

    @Serial
    private static final long serialVersionUID = 6676357003482048951L;

    private Long userId;

    private String username;

    private String password;

    private Set<AuthorityDto> authorities;

    @Override
    public Set<AuthorityDto> getAuthorities() {
        return authorities;
    }

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
