package com.rehe.storage.service;

import com.rehe.storage.model.*;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.ListBucketsResponse;
import software.amazon.awssdk.services.s3.model.MultipartUpload;
import software.amazon.awssdk.services.s3.model.S3Object;

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

    default List<ListObjectResponse> listObjects(ListObjectRequest request) {
        return null;
    }

    default List<String> delete(ListObjectRequest request) {
        return null;
    }

    default List<String> copy(ICopyObjectRequest request) {
        return null;
    }

}