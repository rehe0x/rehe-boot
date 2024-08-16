package com.rehe.storage.weed;

import com.rehe.common.SimpleHttpClient;
import lombok.Builder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * @author xiech
 * @description
 * @date 2024/8/9
 */
@Builder
public class FilerClient {
//    private static final int CHUNK_SIZE = 4 * 1024 * 1024;

    private String filerUrl;

    public String upload(byte[] fileByte,String fileName, String mimeType) {

        HttpResponse<String> response = SimpleHttpClient.getInstance()
                .sendPostMultipart(filerUrl, fileByte, fileName, mimeType);
        if (response.statusCode() == 201) {
            return response.body();
        } else {
            return response.body();
        }
    }

    public String uploadChunk(byte[] chunk,String fileName, String mimeType){

        HttpResponse<String> response = SimpleHttpClient.getInstance()
                        .sendPostMultipart(filerUrl+"?op=append",chunk,fileName,mimeType);
        if (response.statusCode() == 201) {
          return   response.body();
        } else {
            return   response.body();
        }

//
//        URL url = new URL(filerUrl+ "/" +fileName +p);
//        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//        conn.setDoOutput(true);
//        conn.setRequestMethod("POST");
////        conn.setRequestProperty("Content-Md5","sdfsdfadfasd111");
////        conn.setRequestProperty("Content-Length", String.valueOf(chunk.length));
//
//
//        conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);
//        String mimeType = "video/webm";
//        try (DataOutputStream dos = new DataOutputStream(conn.getOutputStream())) {
//            dos.writeBytes("--" + BOUNDARY + "\r\n");
//            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\"" + fileName + "\"\r\n");
////            dos.writeBytes("Content-Type: application/octet-stream\r\n\r\n");
//            dos.writeBytes("Content-Type: " + mimeType + "\r\n\r\n");
//
//            dos.write(chunk);
//
//            // 添加 MD5 信息作为额外字段
//            dos.writeBytes("\r\n--" + BOUNDARY + "\r\n");
//            dos.writeBytes("Content-Disposition: form-data; name=\"Md5\"\r\n\r\n");
//            dos.writeBytes("sdfasdfsadf");
//            dos.writeBytes("\r\n--" + BOUNDARY + "--\r\n");
//
////            dos.writeBytes("\r\n--" + BOUNDARY + "--\r\n");
//            dos.flush();
//        }
//
//        int responseCode = conn.getResponseCode();
//        InputStream inputStream;
//        if (responseCode == 201) {
//            inputStream = conn.getInputStream();
//        } else {
//            inputStream = conn.getErrorStream();
//        }
//        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
//        String inputLine;
//        StringBuilder response = new StringBuilder();
//        while ((inputLine = in.readLine()) != null) {
//            response.append(inputLine);
//        }
//        in.close();
//        conn.disconnect();
//        System.out.println("Response Body: " + response.toString());
//
//        return responseCode >= 200 && responseCode < 300;
    }

}
