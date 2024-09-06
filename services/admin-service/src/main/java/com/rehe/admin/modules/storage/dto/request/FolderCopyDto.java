package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="复制或重命名")
@Data
public class FolderCopyDto {
    @Schema(description = "原始/名称")
    @NotBlank(message = "原始名不能为空")
    private String sourceKey;
    @Schema(description = "目标/名称")
    @NotBlank(message = "目标名不能为空")
    private String targetKey;
}
