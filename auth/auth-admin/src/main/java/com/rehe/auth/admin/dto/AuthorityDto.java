package com.rehe.auth.admin.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author xiech
 * @description
 * @date 2024/7/29
 */
@Data
public class AuthorityDto implements GrantedAuthority {
    private String authority;

    public AuthorityDto(String authority) {
        this.authority = authority;
    }
}
