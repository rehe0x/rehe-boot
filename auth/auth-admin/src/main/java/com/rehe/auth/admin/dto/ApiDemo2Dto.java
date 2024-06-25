package com.rehe.auth.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/1/8
 */
@Data
@Builder
@Schema(description = "示例参数对象二选一")
public class ApiDemo2Dto {
    @Schema(description = "code")
    private String code;
    @Schema(description = "openId")
    private String openId;

    @AssertTrue(message = "code or openId is required")
    private boolean isCodeOrOpenIdExists() {
        return code != null || openId != null;
    }
}
