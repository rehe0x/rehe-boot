package com.rehe.auth.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Data
@Builder
@Schema(description = "登录DTO")
public class AdminLoginDto {
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String username;
    @Schema(description = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;
}
