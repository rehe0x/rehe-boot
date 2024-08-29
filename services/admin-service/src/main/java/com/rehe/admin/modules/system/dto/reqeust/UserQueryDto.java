package com.rehe.admin.modules.system.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "用户列表查询")
@Data
public class UserQueryDto {
    @Schema(description = "关键字")
    private String keyword;
    @Schema(description = "部门ID数组")
    private Long[] deptIds;
}
