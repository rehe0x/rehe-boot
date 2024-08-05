package com.rehe.modules.admin.system.dto.reqeust;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@Schema(description="字典列表新增")
@Data
public class DictDetailCreateDto {

    @Schema(description = "字典ID")
    @NotNull(message = "字典ID不能为空")
    private Long dictId;

    @Schema(description = "字典标签")
    @NotBlank(message = "字典标题不能为空")
    @Size(max = 20, min = 1,message = "字典标题1-20字符")
    private String label;

    @Schema(description = "字典值")
    @NotBlank(message = "字典值不能为空")
    @Size(max = 20, min = 1,message = "字典值1-20字符")
    private String value;

    @Schema(description = "排序")
    private Integer sort;
}