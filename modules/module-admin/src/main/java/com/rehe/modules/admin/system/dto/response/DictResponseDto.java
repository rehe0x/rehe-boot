package com.rehe.modules.admin.system.dto.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@Schema(description="字典")
@Data
public class DictResponseDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 字典代码
     */
    @Schema(description = "字典代码")
    private String code;

    /**
     * 字典名称
     */
    @Schema(description = "字典名称")
    private String name;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}