package com.rehe.admin.modules.system.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    private Set<Long> deptIds;
    private Set<Long> menuIds;


    @Serial
    private static final long serialVersionUID = 1L;
}