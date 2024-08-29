package com.rehe.admin.modules.storage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Schema(description="分段信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PartCheckResponseDto {
    private Integer partNumber;
    private String eTag;
    private long size;
    @Schema(description = "是否完成上传")
    private boolean completion;
}
