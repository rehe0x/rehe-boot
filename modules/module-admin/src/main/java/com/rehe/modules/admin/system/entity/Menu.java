package com.rehe.modules.admin.system.entity;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;
/**
 * @description
 * @author rehe
 * @date 2024/6/26
 */
@Data
public class Menu implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private Long id;

    /**
     * 上级ID 默认0
     */
    private Long parentId;

    /**
     * 菜单类型0=目录 1=菜单 2=权限
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
     * 排序 默认0
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

    /**
     * 创建日期
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;


}