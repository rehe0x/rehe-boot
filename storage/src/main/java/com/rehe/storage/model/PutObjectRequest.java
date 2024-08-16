package com.rehe.storage.model;

import lombok.Builder;
import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Builder
@Data
public class PutObjectRequest {
    private String bucket;
    private String key;
    private byte[] fileByte;
    private String contentType;

}
