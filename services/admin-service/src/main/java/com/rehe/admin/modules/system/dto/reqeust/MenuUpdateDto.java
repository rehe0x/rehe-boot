package com.rehe.admin.modules.system.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;


/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "菜单修改")
@Data
public class MenuUpdateDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 菜单标题
     */
    @Schema(description = "菜单标题")
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

    /**
     * 路由
     */
    @Schema(description = "路由 每一个菜单下可设置一个默认路由")
    private String routePath;


    /**
     * 是否默认路由
     */
    @Schema(description = "是否默认路由 每一个菜单下可设置一个默认路由")
    private Boolean routeDefault = false;


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

}
