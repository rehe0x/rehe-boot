package com.rehe.storage.model;

import lombok.Builder;
import lombok.Data;
import software.amazon.awssdk.services.s3.model.CommonPrefix;
import software.amazon.awssdk.services.s3.model.S3Object;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
@Builder
@Data
public class ListObjectResponse {
//    private List<S3Object> objects;
//    private List<String> folders;

    private  String name;
    private  String md5Hex;
    private  Long size;
    private String mimeType;
    private  boolean folder;
}
