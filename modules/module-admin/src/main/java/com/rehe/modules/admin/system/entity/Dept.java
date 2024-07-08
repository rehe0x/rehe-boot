package com.rehe.modules.admin.system.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/***
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Data
public class Dept implements Serializable {
    /**
    * ID
    */
    private Long id;

    /**
    * 上级部门
    */
    private Long parentId;

    /**
    * 名称
    */
    private String name;

    /**
    * 排序
    */
    private Integer sort;

    /**
    * 状态
    */
    private Boolean enabled;

    /**
    * 创建日期
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}