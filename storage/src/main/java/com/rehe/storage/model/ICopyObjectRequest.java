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
public class ICopyObjectRequest {
    private String bucket;
    private String sourceKey;
    private String targetKey;
}
