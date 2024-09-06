package com.rehe.storage.s3;

import com.rehe.common.exception.BusinessException;
import com.rehe.common.util.MD5Utils;
import com.rehe.storage.model.*;
import com.rehe.storage.model.PutObjectRequest;
import com.rehe.storage.service.BaseStorageService;
import org.springframework.util.StringUtils;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.net.URLConnection;
import java.util.*;
import java.util.regex.Pattern;
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
                .contentLength((long)request.getFileByte().length)
                .build();

        RequestBody requestBody;
        if(request.getFileByte().length == 0){
            requestBody = RequestBody.empty();
        } else {
            requestBody = RequestBody.fromBytes(request.getFileByte());
        }
        try {
            PutObjectResponse response = s3Client.putObject(putObjectRequest, requestBody);
            if (response != null && StringUtils.hasText(response.eTag())) {
                return response.eTag();
            } else {
                if(request.getFileByte().length == 0){
                    return null;
                }
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
            e.printStackTrace();
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


    @Override
    public List<ListObjectResponse> listObjects(ListObjectRequest request) {
        List<ListObjectResponse> list = findObjects(request.getBucket(), request.getPath());
        return list;
    }



    @Override
    public List<String> delete(ListObjectRequest request) {
        List<FolderList> folderLists = new ArrayList<>();
        listObjectsRecursive(request.getBucket(), request.getPath(), folderLists);
        Collections.reverse(folderLists);

        List<ObjectIdentifier> objectIdentifiers = folderLists.stream()
                .map(folderList -> ObjectIdentifier.builder().key(folderList.getKey()).build())
                .collect(Collectors.toList());

        for (ObjectIdentifier objectIdentifier : objectIdentifiers) {
            System.out.println("Key: " + objectIdentifier.key());
        }
        try{
            DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                    .bucket(request.getBucket())
                    .delete(delete -> delete.objects(objectIdentifiers))
                    .build();
            DeleteObjectsResponse response = s3Client.deleteObjects(deleteObjectsRequest);
            if (response.sdkHttpResponse().isSuccessful()) {
                return null;
            } else {
                throw new BusinessException("删除失败");
            }
        }catch (S3Exception e){
            e.printStackTrace();
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }

    @Override
    public List<String> copy(ICopyObjectRequest request) {
        List<FolderList> folderLists = new ArrayList<>();
        listObjectsRecursive(request.getBucket(), request.getSourceKey(), folderLists);
        Collections.reverse(folderLists);

        try{
            for (FolderList folderList : folderLists) {
                String dKey = folderList.getKey().replaceAll(Pattern.quote(request.getSourceKey()),request.getTargetKey());
                System.out.println("Key: " + folderList.getKey()+"==="+dKey);
                boolean r;
                if(folderList.isFolder()){
                    PutObjectRequest pr = PutObjectRequest.builder()
                            .bucket(request.getBucket())
                            .key(dKey)
                            .fileByte(new byte[0])
                            .contentType(null)
                            .build();

                    putObject(pr);
                    r = true;
                } else {
                    CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                            .sourceBucket(request.getBucket())
                            .sourceKey(folderList.getKey())
                            .destinationBucket(request.getBucket())
                            .destinationKey(dKey)
                            .build();
                    CopyObjectResponse response = s3Client.copyObject(copyObjectRequest);
                    r = response.sdkHttpResponse().isSuccessful();
                }

                if (r) {
                    DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                            .bucket(request.getBucket())
                            .key(folderList.getKey())
                            .build();
                    DeleteObjectResponse dr = s3Client.deleteObject(deleteObjectRequest);
                    if (!dr.sdkHttpResponse().isSuccessful()) {
                         throw new BusinessException("删除失败");
                    }
                } else {
                    throw new BusinessException("复制失败");
                }
            }
            return null;
        }catch (S3Exception e){
            e.printStackTrace();
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }

    private void listObjectsRecursive(String bucket, String prefix, List<FolderList> folderLists) {
        ListObjectsV2Request listObjectsRequest = ListObjectsV2Request.builder()
                .bucket(bucket)
                .prefix(prefix)
                .delimiter("/")
                .build();
        ListObjectsV2Response listObjectsResponse;
        do {
            try{
                listObjectsResponse = s3Client.listObjectsV2(listObjectsRequest);

                if (listObjectsResponse.sdkHttpResponse().isSuccessful()) {
                    for (S3Object s3Object : listObjectsResponse.contents()) {
                        folderLists.add(FolderList.builder().key(s3Object.key()).folder(false).build());
                    }

                    for (CommonPrefix commonPrefix : listObjectsResponse.commonPrefixes()) {
                        folderLists.add(FolderList.builder().key(commonPrefix.prefix()).folder(true).build());
                        listObjectsRecursive(bucket, commonPrefix.prefix(), folderLists);
                    }
                    // Update request to get the next set of results if they exist
                    listObjectsRequest = ListObjectsV2Request.builder()
                            .bucket(bucket)
                            .prefix(prefix)
                            .delimiter("/")
                            .continuationToken(listObjectsResponse.nextContinuationToken())
                            .build();
                } else {
                    throw new BusinessException("查询失败");
                }
            }catch (S3Exception e){
                e.printStackTrace();
                throw new BusinessException(e.awsErrorDetails().errorCode());
            }

        } while (listObjectsResponse.isTruncated());
    }


    private List<ListObjectResponse> findObjects(String bucketName, String path) {
        ListObjectsV2Request listObjectsV2Request = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .prefix(path)
                .delimiter("/")
                .build();

        try{
            ListObjectsV2Response response = s3Client.listObjectsV2(listObjectsV2Request);
            if (response.sdkHttpResponse().isSuccessful()) {
                List<ListObjectResponse> objects = response.contents().stream()
                        .map(s3Object -> ListObjectResponse.builder()
                                .name(keyReplace(s3Object.key()))
                                .key(s3Object.key())
                                .md5Hex(eTagReplace(s3Object.eTag()))
                                .size(s3Object.size())
                                .mimeType(URLConnection.guessContentTypeFromName(s3Object.key()))
                                .folder(false)
                                .build())
                        .toList();

                List<ListObjectResponse> folders = response.commonPrefixes().stream()
                        .map(commonPrefix -> ListObjectResponse.builder()
                                .name(prefixeReplace(commonPrefix.prefix()))
                                .mimeType("Folder")
                                .folder(true)
                                .build())
                        .toList();


                List<ListObjectResponse> responseList = new ArrayList<>(folders);
                responseList.addAll(objects);
                return responseList;
            } else {
                throw new BusinessException("获取list object失败");
            }
        }catch (S3Exception e){
            e.printStackTrace();
            throw new BusinessException(e.awsErrorDetails().errorCode());
        }
    }



    private String eTagReplace(String eTag) {
        return eTag.replaceAll("^\"|\"$", "");
    }

    private String keyReplace(String key) {
        return key.substring(key.lastIndexOf("/") + 1);
    }

    private String prefixeReplace(String prefix) {
        if (prefix.endsWith("/")) {
            prefix = prefix.substring(0, prefix.length() - 1);
        }
        return prefix.substring(prefix.lastIndexOf("/") + 1);
    }
}
