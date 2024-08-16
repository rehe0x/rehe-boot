package com.rehe.common;

import com.rehe.common.exception.BusinessException;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author xiech
 * @description
 * @date 2024/8/12
 */
public class SimpleHttpClient {
    // 使用 volatile 修饰，确保多线程环境下的可见性
    private static volatile SimpleHttpClient instance;

    private final HttpClient httpClient;

    // 私有构造函数，防止外部直接实例化
    private SimpleHttpClient() {
        this.httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
    }

    // 双重检查锁定实现单例模式
    public static SimpleHttpClient getInstance() {
        if (instance == null) {
            synchronized (SimpleHttpClient.class) {
                if (instance == null) {
                    instance = new SimpleHttpClient();
                }
            }
        }
        return instance;
    }

    public HttpResponse<String> sendRequest(HttpRequest request){
        try {
            return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            throw new BusinessException(e.getMessage());
        }
    }
    public <T> HttpResponse<T> sendRequest(HttpRequest request, HttpResponse.BodyHandler<T> bodyHandler) {
        try {
            return httpClient.send(request, bodyHandler);
        }catch (IOException | InterruptedException e){
            throw new BusinessException(e.getMessage());
        }
    }

    public CompletableFuture<String> sendRequestAsync(HttpRequest request) {
        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    public HttpResponse<String> sendGet(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return sendRequest(request);
    }

    public <T> HttpResponse<T> sendGet(String url, HttpResponse.BodyHandler<T> bodyHandler)  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        return sendRequest(request,bodyHandler);
    }

    public HttpResponse<String> sendGet(String url,Map<String, String> headers) {
        HttpRequest request = createRequestWithHeaders(url,headers)
                .GET()
                .build();
        return sendRequest(request);
    }


    public <T> HttpResponse<T> sendGet(String url, Map<String, String> headers,HttpResponse.BodyHandler<T> bodyHandler) {
        HttpRequest request = createRequestWithHeaders(url,headers)
                .GET()
                .build();
        return sendRequest(request,bodyHandler);
    }


    public HttpResponse<String> sendPost(String url, String jsonBody)  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request);
    }

    public <T> HttpResponse<T> sendPost(String url, String jsonBody,HttpResponse.BodyHandler<T> bodyHandler)  {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request,bodyHandler);
    }


    public HttpResponse<String> sendPost(String url, String jsonBody, Map<String, String> headers)  {
        HttpRequest request = createRequestWithHeaders(url,headers)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request);
    }

    public <T> HttpResponse<T> sendPost(String url, String jsonBody, Map<String, String> headers,HttpResponse.BodyHandler<T> bodyHandler) throws Exception {
        HttpRequest request = createRequestWithHeaders(url,headers)
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();
        return sendRequest(request,bodyHandler);
    }

    public CompletableFuture<String> sendGetAsync(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();

        return sendRequestAsync(request);
    }

    public CompletableFuture<String> sendPostAsync(String url, String jsonBody) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        return sendRequestAsync(request);
    }

    public HttpResponse<String>  sendPostMultipart(String url,byte[] fileByte,String fileName,String mimeType)  {
        return sendPostMultipart(url,fileByte,fileName,mimeType,null,HttpResponse.BodyHandlers.ofString());
    }

    public  <T> HttpResponse<T> sendPostMultipart(String url,byte[] fileByte,String fileName,String mimeType,HttpResponse.BodyHandler<T> bodyHandler) {
       return sendPostMultipart(url,fileByte,fileName,mimeType,null,bodyHandler);
    }

    public <T> HttpResponse<T> sendPostMultipart(String url,byte[] fileByte,String fileName,String mimeType,Map<Object, Object> data, HttpResponse.BodyHandler<T> bodyHandler)  {
        String boundary = new BigInteger(256, new Random()).toString();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "multipart/form-data;boundary=" + boundary)
                .POST(ofMimeMultipartData(boundary, fileName, mimeType, fileByte, data))
                .build();
       return sendRequest(request,bodyHandler);
    }


    public HttpRequest.Builder createRequestWithHeaders(String url, Map<String, String> headers) {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create(url));
        headers.forEach(builder::header);
        return builder;
    }

    public HttpRequest.BodyPublisher ofMimeMultipartData(String boundary,String fileName,String mimeType,byte[] fileByte,Map<Object, Object> data) {
        var byteArrays = new ArrayList<byte[]>();
        byte[] separator = ("\r\n--" + boundary + "\r\nContent-Disposition: form-data; name=")
                .getBytes(StandardCharsets.UTF_8);

        if(data != null || fileName != null){
            byteArrays.add(separator);
        }

        if(fileName != null) {
            String name = "file";
            byteArrays.add(("\"" + name + "\"; filename=\"" + fileName
                    + "\"\r\nContent-Type: " + mimeType + "\r\n\r\n")
                    .getBytes(StandardCharsets.UTF_8));
            byteArrays.add((byte[]) fileByte);
        }
        if(data != null) {
            for (Map.Entry<Object, Object> entry : data.entrySet()) {
                byteArrays.add(("\"" + entry.getKey() + "\"\r\n\r\n" + entry.getValue())
                        .getBytes(StandardCharsets.UTF_8));
            }
        }
        byteArrays.add(("\r\n--" + boundary + "--\r\n").getBytes(StandardCharsets.UTF_8));
        return HttpRequest.BodyPublishers.ofByteArrays(byteArrays);
    }
}
