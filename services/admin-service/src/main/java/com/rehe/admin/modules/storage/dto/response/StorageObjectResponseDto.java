package com.rehe.admin.modules.storage.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.services.s3.model.Owner;
import software.amazon.awssdk.services.s3.model.RestoreStatus;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.time.Instant;
import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Schema(description="存储对象")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageObjectResponseDto {
    private  String name;
    private  String md5Hex;
    private  Long size;
    private String mimeType;
    private  boolean folder;
}
