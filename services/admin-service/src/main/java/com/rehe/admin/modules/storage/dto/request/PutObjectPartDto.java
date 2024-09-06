package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="文件分段上传")
@Data
public class PutObjectPartDto {
    @Schema(description = "分段上传Id")
    @NotBlank
    private String uploadId;
    @Schema(description = "分段编号从1开始")
    @NotNull
    @Min(1)
    @Max(10000)
    private Integer partNumber;
    @Schema(description = "md5hax")
    @NotBlank
    private String md5hex;
    @NotNull
    @Schema(description = "大小")
    private Long size;

    @Schema(description = "文件路径")
    @NotBlank
    private String path;

}
