package com.rehe.admin.modules.storage.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/13
 */
@Schema(description="对象存储列表查询")
@Data
public class StorageObjectQueryDto {
    private String path;
}
