package com.rehe.modules.admin.system.dto.reqeust;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Schema(description="部门新增")
@Data
public class DeptCreateDto {
    /**
     * 上级部门
     */
    @Schema(description = "上级部门")
    private Long parentId;

    /**
     * 名称
     */
    @Schema(description = "部门名称")
    @NotBlank(message = "部门名称不能为空")
    @Size(max = 20, min = 2,message = "部门名称2-20字符")
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