package com.rehe.modules.admin.system.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @author rehe
 * @date 2024/7/23
 */
@Schema(description = "角色对象")
@Data
public class RoleResponseDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 名称
     */
    @Schema(description = "名称")
    private String name;

    /**
     * 角色级别
     */
    @Schema(description = "角色级别")
    private Integer level;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String description;

    /**
     *
     */
    @Schema(description = "")
    private Integer scope;

    /**
     *
     */
    @Schema(description = "")
    private LocalDateTime createTime;

    /**
     *
     */
    @Schema(description = "")
    private LocalDateTime updateTime;

}