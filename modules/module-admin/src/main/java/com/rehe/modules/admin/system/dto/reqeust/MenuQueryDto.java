package com.rehe.modules.admin.system.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "菜单列表查询")
@Data
public class MenuQueryDto  {
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "系统ID")
    @NotNull(message = "系统ID不能为空")
    private Integer platformId;
}
