package com.rehe.auth.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @description
 * @author rehe
 * @date 2024/6/26
 */
@Data
public class AuthMenuDto implements Serializable {
    /**
     * ID
     */
    private Long id;

    /**
     * 上级ID 默认0
     */
    private Long parentId;

    /**
     * 菜单类型0目录 1菜单 2权限
     */
    private Integer menuType;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 菜单名称扩展字段
     */
    private String name;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 路由
     */
    private String routePath;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否外链
     */
    private Boolean link;

    /**
     * 缓存
     */
    private Boolean cache;

    /**
     * 隐藏
     */
    private Boolean hidden;

    /**
     * 权限
     */
    private String permission;

    private Integer platformId;


    @Serial
    private static final long serialVersionUID = 1L;
}