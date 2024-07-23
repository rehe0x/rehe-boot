package com.rehe.modules.admin.system.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Schema(description="部门新增")
@Data
public class DeptUpdateDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
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
    
}