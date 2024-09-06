package com.rehe.admin.modules.storage.dto.request;

import com.rehe.admin.modules.storage.dto.request.PartCheckDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="分段信息验证")
@Data
public class PartCheckDto {
    @Schema(description = "文件路径/名称")
    @NotBlank
    private String key;
    @Schema(description = "分段上传Id")
    @NotBlank
    private String uploadId;
    @Schema(description = "分段明细")
    @NotEmpty(message = "分段明细不能为空")
    @Valid
    private List<PartCheckDetailDto> detailList;

    @Schema(description = "文件路径")
    @NotBlank
    private String path;
}
