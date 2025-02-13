package com.rehe.biz.core.common.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Schema(description = "分页对象")
@Data
public class PageParamDto {
    @Schema(description = "页码 默认1开始")
    private int pageNum = 1;
    @Schema(description = "每页条数默认20")
    private int pageSize = 20;
}
