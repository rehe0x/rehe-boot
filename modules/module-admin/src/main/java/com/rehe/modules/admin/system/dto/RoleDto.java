package com.rehe.modules.admin.system.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @description
 * @author rehe
 * @date 2024/7/23
 */
@Data
public class RoleDto implements Serializable {
    /**
    * ID
    */
    private Long id;

    /**
    * 名称
    */
    private String name;

    /**
    * 角色级别
    */
    private Integer level;

    /**
    * 描述
    */
    private String description;

    private Integer scope;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}