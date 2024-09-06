package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="查看上传")
@Data
public class CheckMultipartUpload {
    @Schema(description = "文件路径/名称")
    @NotBlank(message = "key不能为空")
    private String key;

    @Schema(description = "文件路径")
    @NotBlank
    private String path;

    @Schema(description = "分段上传ID")
    @NotBlank(message = "id不能为空")
    private String uploadId;

}
