package com.rehe.admin.modules.system.dto.reqeust;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

/**
 * @description
 * @author rehe
 * @date 2024/7/23
 */
@Schema(description = "角色关联菜单对象")
@Data
public class RoleMenuBindDto {

    @Schema(description = "角色ID")
    @NotNull(message = "角色ID不能为空")
    private Long id;

    @Schema(description = "菜单id列表")
    private Set<Long> menuIds;

}