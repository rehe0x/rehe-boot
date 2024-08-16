package com.rehe.storage.s3;

import com.rehe.common.exception.BusinessException;
import com.rehe.common.util.MD5Utils;
import com.rehe.storage.model.PartCheckRequest;
import com.rehe.storage.model.PartCheckResponse;
import com.rehe.storage.model.PutObjectPartRequest;
import com.rehe.storage.model.PutObjectRequest;
import com.rehe.storage.service.BaseStorageService;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author xiech
 * @description
 * @date 2024/8/8
 */
public class S3Service implements BaseStorageService {
    private final S3Client s3Client;

    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String putObject(PutObjectRequest request) {
        software.amazon.awssdk.services.s3.model.PutObjectRequest putObjectRequest = software.amazon.awssdk.services.s3.model.PutObjectRequest.builder()
                .bucket(request.getBucket())
                .key(request.getKey())
                .contentType(request.getContentType())
                .build();

        RequestBody requestBody = RequestBody.fromBytes(request.getFileByte());
        try {
            PutObjectResponse response = s3Client.putObject(putObjectRequest, requestBody);
            if (response != null && StringUtils.hasText(response.eTag())) {
                return response.eTag();
            } else {
                throw new BusinessException("s3上传失败");
            }
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }


    @Override
    public String headObject(String bucket, String key) {
        HeadObjectRequest headObjectRequest = HeadObjectRequest.builder().bucket(bucket).key(key).build();

        try {
            HeadObjectResponse response = s3Client.headObject(headObjectRequest);
            return response.eTag();
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }

    }

    @Override
    public String createMultipartUpload(PutObjectRequest request) {
        if(!StringUtils.hasText(request.getContentType())){
            request.setContentType("application/octet-stream");
        }
        CreateMultipartUploadRequest createMultipartUploadRequest = CreateMultipartUploadRequest.builder()
                .bucket(request.getBucket())
                .key(request.getKey())
                .contentType(request.getContentType())
                .build();
        try {
            CreateMultipartUploadResponse response = s3Client.createMultipartUpload(createMultipartUploadRequest);
            if (response.sdkHttpResponse().isSuccessful()) {
                return response.uploadId();
            } else {
                throw new BusinessException("分段上传创建失败");
            }
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }


    @Override
    public String putObjectPart(PutObjectPartRequest request) {

        if (!StringUtils.hasText(request.getETag())) {
            request.setETag(MD5Utils.hashToHexString(request.getFileByte()));
        }
        if(!request.getETag().equals(MD5Utils.hashToHexString(request.getFileByte()))){
            throw new BusinessException("etag不一致");
        }

        if(request.getSize() != request.getFileByte().length){
            throw new BusinessException("size不一致");
        }

        Optional<List<Part>> ps = listParts(request);

        if (ps.isPresent() && !ps.get().isEmpty()) {
            if (ps.get().size() > 1) {
                throw new BusinessException("检查已有多个相同分段");
            }
            Part p = ps.get().get(0);
            String e = eTagReplace(p.eTag());
            if (e.equals(request.getETag())) {
                return e;
            } else {
                throw new BusinessException("检查已有分段etag异常");
            }
        }
        UploadPartRequest uploadPartRequest = UploadPartRequest.builder()
                .bucket(request.getBucket())
                .key(request.getKey())
                .uploadId(request.getUploadId())
                .partNumber(request.getPartNumber())
                .build();

        try {
            UploadPartResponse response = s3Client.uploadPart(uploadPartRequest, RequestBody.fromBytes(request.getFileByte()));
            if (response.sdkHttpResponse().isSuccessful()) {
                String e = eTagReplace(response.eTag());
                if (!e.equals(request.getETag())) {
                    throw new BusinessException("分段上传etag异常");
                }
                request.setPartNumber(null);
                listParts(request);
                return e;
            } else {
                throw new BusinessException("part上传异常");
            }
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }

    @Override
    public String completeMultipartUpload(PutObjectPartRequest request) {
        request.setPartNumber(null);
        List<Part> partList = listParts(request).orElseThrow(() -> new BusinessException("part数据异常"));
        List<CompletedPart> completedParts = partList.stream()
                .map(part -> CompletedPart.builder().partNumber(part.partNumber()).eTag(part.eTag()).build())
                .collect(Collectors.toList());
        CompleteMultipartUploadRequest completeMultipartUploadRequest = CompleteMultipartUploadRequest.builder()
                .bucket(request.getBucket())
                .key(request.getKey())
                .uploadId(request.getUploadId())
                .multipartUpload(CompletedMultipartUpload.builder().parts(completedParts).build())
                .build();
        try {
            CompleteMultipartUploadResponse response = s3Client.completeMultipartUpload(completeMultipartUploadRequest);
            if (response.sdkHttpResponse().isSuccessful()) {
                return response.eTag();
            } else {
                throw new BusinessException("合并文件异常");
            }
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }


    @Override
    public Optional<MultipartUpload> checkMultipartUpload(String bucketName, String key, String uploadId) {
        List<MultipartUpload> multipartUploads = listMultipartUploads(bucketName, key);
        if (multipartUploads.isEmpty()) {
            return Optional.empty();
        }
        return multipartUploads.stream().filter(m -> m.uploadId().equals(uploadId)).findFirst();
    }


    @Override
    public List<PartCheckResponse> checkObjectPartList(String bucket, String key, String uploadId, List<PartCheckRequest> partCheckRequestList) {
        PutObjectPartRequest putObjectChunkRequest = PutObjectPartRequest.builder()
                .bucket(bucket)
                .key(key)
                .uploadId(uploadId)
                .build();
        Optional<List<Part>> partList = listParts(putObjectChunkRequest);
        if (partList.isPresent() && partList.get().isEmpty()) {
            return partCheckRequestList.stream()
                    .map(partCheckRequest -> new PartCheckResponse(partCheckRequest.getPartNumber(), partCheckRequest.getETag(), partCheckRequest.getSize(), false))
                    .toList();
        }

        Optional<Part> maxPart = partList.get().stream().max(Comparator.comparingInt(Part::partNumber));
        int maxPartNumber = maxPart.map(Part::partNumber)
                .orElseThrow(() -> new BusinessException("Part list is empty"));

        if (maxPartNumber > partCheckRequestList.size()) {
            throw new BusinessException("part list 数据异常");
        }
        // 按 partNumber 分组
        Map<Integer, List<Part>> mapPart = partList.get().stream().collect(Collectors.groupingBy(Part::partNumber));

        int i = 1;
        List<PartCheckResponse> partCheckResponseList = new ArrayList<>();
        for (PartCheckRequest partRequest : partCheckRequestList) {
            if (!partRequest.getPartNumber().equals(i)) {
                throw new BusinessException("part number错误");
            }
            List<Part> parts = mapPart.get(partRequest.getPartNumber());
            if (parts != null && !parts.isEmpty() && parts.size() > 1) {
                throw new BusinessException("part list数据异常");
            }
            if (parts == null || parts.isEmpty()) {
                // part未上传
                partCheckResponseList.add(new PartCheckResponse(partRequest.getPartNumber(), partRequest.getETag(), partRequest.getSize(), false));
            } else {
                String e = eTagReplace(parts.get(0).eTag());
                if(!StringUtils.hasText(e)){
                    throw new BusinessException("part etag is null");
                }
                if (StringUtils.hasText(partRequest.getETag()) && !e.equals(partRequest.getETag())) {
                    throw new BusinessException("part eTag数据异常");
                }
                if (!parts.get(0).size().equals(partRequest.getSize())) {
                    throw new BusinessException("part size数据异常");
                }
                // part上传完成
                partCheckResponseList.add(new PartCheckResponse(partRequest.getPartNumber(), e, partRequest.getSize(), true));
            }
            i++;
        }
        return partCheckResponseList;
    }


    private List<MultipartUpload> listMultipartUploads(String bucketName, String key) {
        ListMultipartUploadsRequest listMultipartUploadsRequest = ListMultipartUploadsRequest.builder()
                .keyMarker(key)
                .bucket(bucketName)
                .build();
        try{
            ListMultipartUploadsResponse response = s3Client.listMultipartUploads(listMultipartUploadsRequest);
            for (MultipartUpload upload : response.uploads()) {
                System.out.println("Key: " + upload.key());
                System.out.println("UploadId: " + upload.uploadId());
                System.out.println("Initiated: " + upload.initiated());
                System.out.println("-----------------------------------");
            }
            if (response.sdkHttpResponse().isSuccessful()) {
                return response.uploads();
            } else {
                throw new BusinessException("请求异常");
            }
        }catch (S3Exception e){
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }


    private Optional<List<Part>> listParts(PutObjectPartRequest request) {
        ListPartsRequest listPartsRequest = ListPartsRequest.builder()
                .bucket(request.getBucket())
                .key(request.getKey())
                .uploadId(request.getUploadId())
                .build();

        try{
            ListPartsResponse response = s3Client.listParts(listPartsRequest);
            for (Part part : response.parts()) {
                System.out.println("Part Number: " + part.partNumber());
                System.out.println("ETag: " + part.eTag());
                System.out.println("Size: " + part.size());
                System.out.println("Last Modified: " + part.lastModified());
                System.out.println("-----------------------------------");
            }
            if (response.sdkHttpResponse().isSuccessful()) {
                if(request.getPartNumber() != null){
                    return Optional.of(response.parts().stream().filter(m -> m.partNumber().equals(request.getPartNumber())).toList());
                }
                return Optional.ofNullable(response.parts());
            } else {
                throw new BusinessException("获取part失败");
            }
        }catch (S3Exception e){
            e.printStackTrace();
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }

    }

    private String eTagReplace(String eTag) {
        return eTag.replaceAll("^\"|\"$", "");
    }
}
