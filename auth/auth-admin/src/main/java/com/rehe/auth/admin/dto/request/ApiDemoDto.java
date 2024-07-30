package com.rehe.auth.admin.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Data
@Builder
@Schema(description = "示例参数对象")
public class ApiDemoDto {
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
