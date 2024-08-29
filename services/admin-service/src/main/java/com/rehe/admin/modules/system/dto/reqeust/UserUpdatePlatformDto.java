package com.rehe.admin.modules.system.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * @author rehe
 */
@Schema(description="更新用户系统ID")
@Data
public class UserUpdatePlatformDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * ID
     */
    @Schema(description = "系统Id")
    @NotNull(message = "系统Id不能为空")
    private Integer platformId;
}