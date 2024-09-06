package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="创建分段上传")
@Data
public class CreateMultipartUploadDto {
    @Schema(description = "文件路径")
    @NotBlank
    private String path;
    @Schema(description = "文件名称")
    @NotBlank
    private String key;
    @Schema(description = "文件类型")
//    @NotBlank
    private String contentType;
}
