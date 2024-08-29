package com.rehe.admin.common.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.storage.service.BaseStorageService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @description
 * @author rehe
 * @date 2024/8/8
 */
@Tag(name = "文件上传")
@ApiSupport(order = 10)
@RestController
@RequestMapping("/api/v1/file")
public class FileController {
    private final BaseStorageService baseStorageService;

    public FileController(@Qualifier("adminS3Service") BaseStorageService baseStorageService) {
        this.baseStorageService = baseStorageService;
    }

//    @Operation(summary = "文件上传",operationId = "1")
//    @PostMapping("/upload")
//    public Result<String> put(@RequestPart("file") MultipartFile file) {
//        try (InputStream fileStream = file.getInputStream()) {
//            String bucketName = "s3";
//            String key = "file/"+file.getOriginalFilename();
//            String eTag = storageService.putObject(bucketName,key,fileStream.readAllBytes(),file.getContentType());
//            return Result.ok(eTag);
//        } catch (Exception e) {
//           throw new BusinessException(e.getMessage());
//        }
//    }


//    @Operation(summary = "图片上传",operationId = "1")
//    @PostMapping("/image")
//    public Result<String> upload(@RequestPart("file") MultipartFile file) {
//        try (InputStream fileStream = file.getInputStream()) {
//            String bucketName = "s3";
//            String key = file.getOriginalFilename();
//            String eTag = s3Service.putObject(bucketName,key,fileStream,file.getSize(),file.getContentType());
//            return Result.ok(eTag);
//        } catch (Exception e) {
//            throw new BusinessException(e.getMessage());
//        }
//    }
//
//
//    @GetMapping("/s31/upload")
//    public String uploadFile() throws Exception{
//        String bucketName = "s3";
//        String key = "img/test11.jpg";
//        String filePath = "/Users/rehe/Downloads/e3204449e8c642a49c1794517198a7b6.png";
//        s3Service.uploadFile(bucketName, key, filePath);
//        return "File uploaded!";
//    }
//
//    @GetMapping("/s3/uploads")
//    public String uploadFiles() throws Exception{
//        String bucketName = "s3";
//        String key = "img/ff123.webm";
//        String filePath = "/Users/rehe/Downloads/ff123.webm";
//        s3Service.uploadToS3(bucketName, key, new File(filePath));
//        return "File uploaded!";
//    }
//
//
//    @GetMapping("/s3/list")
//    public String listMultipartUploads() throws Exception{
//        String bucketName = "s3";
//        String key = "img/ff123.webm";
//        String filePath = "/Users/rehe/Downloads/ff123.webm";
//        s3Service.listMultipartUploads(bucketName);
//        return "File uploaded!";
//    }
//
//
//    @GetMapping("/s3/part")
//    public String listParts() throws Exception{
//        String bucketName = "s3";
//        String key = "img/ff123.webm";
//        String filePath = "/Users/rehe/Downloads/ff123.webm";
//        s3Service.listParts(bucketName,key,"afc761db05d29f878b14f46443ba2123e17e4290_c991855a18bf42e8ad5202b360fff8f0");
//        return "File uploaded!";
//    }

}