package com.rehe.storage.s3;

import lombok.Data;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */

@Data
public class S3Properties {
    private String endpoint;

    private String region;

    private String accessKeyId;

    private String secretKey;
}
