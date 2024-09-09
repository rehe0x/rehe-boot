package com.rehe.admin.modules.storage;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.admin.modules.storage.dto.response.StorageObjectResponseDto;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.redis.DistributedLock;
import com.rehe.common.result.Result;
import com.rehe.admin.modules.storage.dto.request.*;
import com.rehe.admin.modules.storage.dto.response.PartCheckResponseDto;
import com.rehe.storage.model.*;
import com.rehe.storage.s3.S3Properties;
import com.rehe.storage.service.BaseStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

/**
 * @author rehe
 * @description
 * @date 2024/8/8
 */
@Tag(name = "对象存储")
@ApiSupport(order = 10)
@RestController
@RequestMapping("/api/v1/storage")
public class StorageController {

    private final BaseStorageService baseStorageService;

    private final S3Properties s3Properties;

    private final String bucketName = "s3";

    private final String keyPrefix = "storage";

    public StorageController(@Qualifier("adminS3Service") BaseStorageService baseStorageService,
            @Qualifier("adminS3Properties") S3Properties adminS3Properties) {
        this.baseStorageService = baseStorageService;
        this.s3Properties = adminS3Properties;
    }


    @Operation(summary = "对象存储列表", operationId = "1")
    @PreAuthorize("hasAuthority('storage')")
    @GetMapping("/list")
    public Result<List<StorageObjectResponseDto>> list(@ParameterObject @Valid StorageObjectQueryDto dto) {
        ListObjectRequest listObjectRequest = ListObjectRequest.builder()
                .bucket(bucketName)
                .path(keyPrefix+"/"+ (dto.getPath() == null ? "" : dto.getPath()))
                .build();
        List<ListObjectResponse> responses = baseStorageService.listObjects(listObjectRequest);

        List<StorageObjectResponseDto> list = responses.stream().map(p ->
                        StorageObjectResponseDto.builder()
                                .url(!p.isFolder() ? s3Properties.getEndpoint()+"/"+bucketName+"/"+p.getKey() : "")
                                .name(p.getName())
                                .md5Hex(p.getMd5Hex())
                                .size(p.getSize())
                                .mimeType(p.getMimeType())
                                .folder(p.isFolder())
                                .build())
                .toList();

        return Result.ok(list);
    }


    @Operation(summary = "新建文件夹", operationId = "11")
    @PreAuthorize("hasAuthority('storage:newfolder')")
    @PostMapping("/folder/create")
    public Result<String> newFolder(@RequestBody @Valid FolderCrudDto dto) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(keyPrefix+"/"+dto.getPath()+"/")
                    .fileByte(new byte[0])
                    .contentType(null)
                    .build();

            String eTag = baseStorageService.putObject(request);
            return Result.ok(eTag);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Operation(summary = "删除对象", operationId = "12")
    @PreAuthorize("hasAuthority('storage:delete')")
    @PostMapping("/folder/delete")
    public Result<Void> delete(@RequestBody @Valid FolderCrudDto dto) {
        try {
            ListObjectRequest listObjectRequest = ListObjectRequest.builder()
                    .bucket(bucketName)
                    .path(keyPrefix+"/"+dto.getPath())
                    .build();
            baseStorageService.delete(listObjectRequest);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @Operation(summary = "对象重命名", operationId = "13")
    @PreAuthorize("hasAuthority('storage:rename')")
    @PostMapping("/folder/rename")
    public Result<Void> rename(@RequestBody @Valid FolderCopyDto dto) {
        try {
            ICopyObjectRequest iCopyObjectRequest = ICopyObjectRequest.builder()
                    .bucket(bucketName)
                    .sourceKey(keyPrefix+"/"+dto.getSourceKey())
                    .targetKey(keyPrefix+"/"+dto.getTargetKey())
                    .build();
            baseStorageService.copy(iCopyObjectRequest);
            return Result.ok();
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }




//    @Operation(summary = "文件上传", operationId = "1")
//    @PostMapping("/upload")
//    public Result<String> putObject(@RequestPart("file") MultipartFile file) {
//        try (InputStream fileStream = file.getInputStream()) {
//            String key = keyPrefix+"/" + file.getOriginalFilename();
//
//            PutObjectRequest request = PutObjectRequest.builder()
//                    .bucket(bucketName)
//                    .key(key)
//                    .fileByte(fileStream.readAllBytes())
//                    .contentType(file.getContentType())
//                    .build();
//
//            String eTag = baseStorageService.putObject(request);
//            return Result.ok(eTag);
//        } catch (Exception e) {
//            throw new BusinessException(e.getMessage());
//        }
//    }


    @Operation(summary = "创建分段上传", operationId = "20")
    @PreAuthorize("hasAuthority('storage:upload')")
    @PostMapping("/upload/id")
    public Result<String> createMultipartUpload(@RequestBody @Valid CreateMultipartUploadDto dto) {
        String key = keyPrefix+"/" +dto.getPath()  + dto.getKey();
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(dto.getContentType())
                .build();

        String uploadId = baseStorageService.createMultipartUpload(request);
        return Result.ok(uploadId);
    }

    @Operation(summary = "文件分段上传", operationId = "22")
    @PreAuthorize("hasAuthority('storage:upload')")
    @PostMapping("/upload/part")
    public Result<String> putObjectPart(@RequestPart("file") MultipartFile file,
                                        @ModelAttribute @Valid PutObjectPartDto dto) {
        try (InputStream fileStream = file.getInputStream()) {
            String  eTag =  DistributedLock.getINSTANCE().lock(dto.getUploadId(),()->{
                String key = keyPrefix+"/" +dto.getPath()  + file.getOriginalFilename();

                PutObjectPartRequest request = PutObjectPartRequest.builder()
                        .bucket(bucketName)
                        .key(key)
                        .fileByte(fileStream.readAllBytes())
                        .uploadId(dto.getUploadId())
                        .partNumber(dto.getPartNumber())
                        .eTag(dto.getMd5hex())
                        .size(dto.getSize())
                        .build();

                return baseStorageService.putObjectPart(request);
            });
            return Result.ok(eTag);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
    }


    @Operation(summary = "文件分段完成", operationId = "23")
    @PreAuthorize("hasAuthority('storage:upload')")
    @PostMapping("/upload/complete")
    public Result<String> completeMultipartUpload(@RequestBody @Valid CompleteMultipartUploadDto dto) {
        String key = keyPrefix+"/" +dto.getPath() + dto.getKey();
        PutObjectPartRequest request = PutObjectPartRequest.builder()
                .bucket(bucketName)
                .key(key)
                .uploadId(dto.getUploadId())
                .size(dto.getSize())
                .build();

        baseStorageService.checkMultipartUpload(request.getBucket(), request.getKey(), request.getUploadId())
                .orElseThrow(() -> new BusinessException("uploadId不存在"));


        List<PartCheckRequest> partCheckRequestList = dto.getDetailList().stream()
                .map(part ->  PartCheckRequest.builder()
                        .partNumber(part.getPartNumber())
                        .eTag(part.getMd5hex(true))
                        .size(part.getSize())
                        .build())
                .toList();
        List<PartCheckResponse> responses = baseStorageService.checkObjectPartList(bucketName, key, dto.getUploadId(), partCheckRequestList);
        boolean hasActive = responses.stream()
                .anyMatch(o -> !o.isCompletion());
        if(hasActive) {
            throw new BusinessException("有分段未上传无法完成合并");
        }

        String eTag = baseStorageService.completeMultipartUpload(request);
        return Result.ok(eTag);
    }


    @Operation(summary = "查看分段上传", operationId = "25")
    @PreAuthorize("hasAuthority('storage:upload')")
    @PostMapping("/check/upload")
    public Result<Boolean> checkMultipartUpload(@RequestBody @Valid CheckMultipartUploadDto dto) {
        String key = keyPrefix+"/" +dto.getPath()  + dto.getKey();
        boolean b = baseStorageService.checkMultipartUpload(bucketName, key, dto.getUploadId())
                .isPresent();
        return Result.ok(b);
    }


    @Operation(summary = "获取分段信息", operationId = "27")
    @PreAuthorize("hasAuthority('storage:upload')")
    @PostMapping("/check/part")
    public Result<List<PartCheckResponseDto>> checkObjectPartList(@RequestBody @Valid PartCheckDto dto) {
        String key = keyPrefix+"/" +dto.getPath()  + dto.getKey();
        List<PartCheckRequest> partCheckRequestList = dto.getDetailList().stream()
                .map(part -> PartCheckRequest.builder()
                        .partNumber(part.getPartNumber())
                        .eTag(part.getMd5hex())
                        .size(part.getSize())
                        .build())
                .toList();

        List<PartCheckResponse> responses = baseStorageService.checkObjectPartList(bucketName, key, dto.getUploadId(), partCheckRequestList);
        List<PartCheckResponseDto> list = responses.stream().map(p ->
                        new PartCheckResponseDto(p.getPartNumber(), p.getETag(), p.getSize(), p.isCompletion()))
                .toList();
        return Result.ok(list);
    }

}