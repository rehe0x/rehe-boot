package com.rehe.auth.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiech
 * @since 2024-01-07
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @Serial
    private static final long serialVersionUID = -8297955758024753512L;

    private Long id;

    private String avatar;

    private String nickname;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Boolean enabled;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private String openId;
}
