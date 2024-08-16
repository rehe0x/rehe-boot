package com.rehe.storage.weed;

import com.rehe.storage.common.FileUtil;
import com.rehe.storage.model.PartCheckRequest;
import com.rehe.storage.model.PartCheckResponse;
import com.rehe.storage.model.PutObjectPartRequest;
import com.rehe.storage.model.PutObjectRequest;
import com.rehe.storage.service.BaseStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.model.MultipartUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * @author xiech
 * @description
 * @date 2024/8/9
 */
@Service
public class FilerService implements BaseStorageService {
    private final int chunkSize = 1000 * 1024 * 1024;
    @Autowired
    private FilerClient filerClient;

    @Override
    public String putObject(PutObjectRequest request) {
        return "";
    }

    @Override
    public String createMultipartUpload(PutObjectRequest request) {
        return "";
    }

    @Override
    public String putObjectPart(PutObjectPartRequest request) {
        return "";
    }

    @Override
    public String completeMultipartUpload(PutObjectPartRequest request) {
        return "";
    }

    @Override
    public Optional<MultipartUpload> checkMultipartUpload(String bucketName, String key, String uploadId) {
        return Optional.empty();
    }

    @Override
    public List<PartCheckResponse> checkObjectPartList(String bucket, String key, String uploadId, List<PartCheckRequest> partCheckRequestList) {
        return List.of();
    }

    public void uploadChunk(String filePath, String fileName, String mimeType) throws IOException {
        List<byte[]> chunks = FileUtil.splitFile(filePath, chunkSize);
        uploadChunk(chunks,fileName,mimeType);
    }

    public void uploadChunk(File file, String fileName,String mimeType) throws IOException {
        List<byte[]> chunks = FileUtil.splitFile(file, chunkSize);
        uploadChunk(chunks,fileName,mimeType);
    }

    private void uploadChunk(List<byte[]> chunks,String fileName,String mimeType) {
        for (byte[] chunk : chunks) {
            String success = filerClient.uploadChunk(chunk,fileName,mimeType);
        }
    }

//    @Override
//    public String putObject(String bucket, String key, byte[] fileByte, String contentType) {
//        String success = filerClient.uploadChunk(fileByte,bucket + "/" + key,contentType);
//        return success;
//    }
//
//    @Override
//    public String putObjectChunk(String bucket, String key, byte[] fileByte, Integer partNumber, String contentType) {
//        return "";
//    }
//
//    @Override
//    public String putObjectChunk(String bucket, String key, List<byte[]> chunks, String contentType) {
//        return "";
//    }

    @Override
    public String headObject(String bucket, String key) {
        return "";
    }
}
