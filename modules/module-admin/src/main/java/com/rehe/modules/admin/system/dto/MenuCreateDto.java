package com.rehe.modules.admin.system.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import org.springframework.util.StringUtils;


/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "菜单新增")
@Data
public class MenuCreateDto {
    /**
     * 上级ID 默认0
     */
    @Schema(description = "上级ID 默认0")
    private Long parentId;

    /**
     * 菜单类型0目录 1菜单 2权限
     */
    @NotNull(message = "菜单类型不能为空")
    @Max(value = 2,message = "菜单类型错误")
    @Min(value = 0,message = "菜单类型错误")
    @Schema(description = "菜单类型 0=目录 1=菜单 2=权限")
    private Integer menuType;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单名称")
    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 20, min = 2,message = "菜单名称2-20字符")
    private String title;

    /**
     * 菜单名称扩展字段 暂时不用
     */
    @Schema(description = "菜单名称扩展字段")
    private String name;

    /**
     * 组件路径
     */
    @Schema(description = "组件路径 菜单必填")
    private String component;

    @AssertTrue(message = "菜单必填组件路径")
    private boolean isComponentNotBlank() {
        return menuType != 1 || StringUtils.hasText(component);
    }

    /**
     * 路由
     */
    @Schema(description = "路由 每一个菜单下可设置一个默认路由")
    private String routePath;


    /**
     * 是否默认路由
     */
    @Schema(description = "是否默认路由 菜单才可以设置默认路由")
    private Boolean routeDefault = false;


    @AssertTrue(message = "非权限菜单必填路由")
    private boolean isRoutePathNotBlank() {
        return menuType == 2 || (menuType == 1 && routeDefault) || StringUtils.hasText(routePath);
    }


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

    @AssertTrue(message = "外链必须是菜单")
    private boolean isLinkTrue() {
      return (link == null || !link) || menuType == 1;
    }

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
    @Schema(description = "权限标识")
    private String permission;

    @AssertTrue(message = "菜单和权限必填标识")
    private boolean isPermissionNotBlank() {
        return menuType == 0 || StringUtils.hasText(permission);
    }
    @Schema(description = "系统ID")
    @NotNull(message = "系统ID不能为空")
    private Integer platformId;
}
