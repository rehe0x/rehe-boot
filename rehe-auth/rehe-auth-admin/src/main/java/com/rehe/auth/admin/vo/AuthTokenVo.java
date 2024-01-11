package com.rehe.auth.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/1/11
 */

@Data
@Builder
@Schema(description = "登录返回Vo")
public class AuthTokenVo {
    @Schema(description = "token")
    private String token;
    @Schema(description = "刷新token")
    private String refreshToken;
}
