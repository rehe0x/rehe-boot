package com.rehe.admin.modules.system.dto.reqeust;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@Schema(description="字典新增")
@Data
public class DictCreateDto {


    @Schema(description = "字典code")
    @NotBlank(message = "字典code不能为空")
    @Size(max = 20, min = 2,message = "字典code名称2-20字符")
    private String code;

    @Schema(description = "字典名称")
    @NotBlank(message = "字典名称不能为空")
    @Size(max = 20, min = 2,message = "字典名称2-20字符")
    private String name;
}