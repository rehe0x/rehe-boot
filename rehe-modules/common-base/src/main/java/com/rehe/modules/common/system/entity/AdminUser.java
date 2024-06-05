package com.rehe.modules.common.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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
@TableName("admin_user")
public class AdminUser implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
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
}
