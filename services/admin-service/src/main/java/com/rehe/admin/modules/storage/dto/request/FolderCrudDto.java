package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="创建或删除文件")
@Data
public class FolderCrudDto {
    @Schema(description = "文件路径/名称")
    @NotBlank(message = "文件path不能为空")
    private String path;
}
