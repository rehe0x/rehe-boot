package com.rehe.modules.admin.system.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Schema(description="部门")
@Data
public class DeptResponseDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 上级部门
     */
    @Schema(description = "上级部门")
    private Long parentId;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer sort;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean enabled;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}