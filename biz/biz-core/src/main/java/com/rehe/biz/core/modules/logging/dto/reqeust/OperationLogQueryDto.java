package com.rehe.biz.core.modules.logging.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "系统日志列表")
@Data
public class OperationLogQueryDto {
    @Schema(description = "关键字")
    private String keyword;
}
