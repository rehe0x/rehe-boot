package com.rehe.auth.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @author rehe
 * @date 2024/6/26
 */
@Schema(description="登录用户菜单")
@Data
public class AuthMenuVo implements Serializable {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 上级ID 默认0
     */
    @Schema(description = "上级ID 默认0")
    private Long parentId;

    /**
     * 菜单类型0目录 1菜单 2权限
     */
    @Schema(description = "菜单类型0目录 1菜单 2权限")
    private Integer menuType;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单标题")
    private String title;

    /**
     * 菜单名称扩展字段
     */
    @Schema(description = "菜单名称扩展字段")
    private String name;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径")
    private String component;

    /**
     * 路由
     */
    @Schema(description = "路由")
    private String routePath;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 是否外链
     */
    @Schema(description = "是否外链")
    private Boolean link;

    /**
     * 缓存
     */
    @Schema(description = "缓存")
    private Boolean cache;

    /**
     * 隐藏
     */
    @Schema(description = "隐藏")
    private Boolean hidden;

    /**
     * 权限
     */
    @Schema(description = "权限")
    private String permission;

    @Schema(description = "系统ID")
    private Long platformId;


    @Serial
    private static final long serialVersionUID = 1L;
}