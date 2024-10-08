package com.rehe.admin.modules.system.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Data
public class DeptDto implements Serializable {
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