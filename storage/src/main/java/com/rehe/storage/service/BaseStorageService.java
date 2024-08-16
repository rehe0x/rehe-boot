package com.rehe.storage.service;

import com.rehe.storage.model.PartCheckRequest;
import com.rehe.storage.model.PartCheckResponse;
import com.rehe.storage.model.PutObjectPartRequest;
import com.rehe.storage.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.MultipartUpload;

import java.util.List;
import java.util.Optional;

/**
 * @author xiech
 * @description
 * @date 2024/8/11
 */
public interface BaseStorageService {
    String putObject(PutObjectRequest request);

    String headObject(String bucket, String key);

    String createMultipartUpload(PutObjectRequest request);

    String putObjectPart(PutObjectPartRequest request);

    String completeMultipartUpload(PutObjectPartRequest request);

    Optional<MultipartUpload> checkMultipartUpload(String bucketName, String key, String uploadId);

    List<PartCheckResponse> checkObjectPartList(String bucket, String key, String uploadId, List<PartCheckRequest> partCheckRequestList);


}