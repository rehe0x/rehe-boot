package com.rehe.common.result;

import lombok.Getter;
import lombok.Setter;

/**
 * @author xiech
 * @description
 * @date 2024/1/10
 */
@Getter
@Setter
public class HttpError {
    private int error;
    private String message;
    private String path;
    private long timestamp;

    public HttpError(int error, String message, String path) {
        this.error = error;
        this.message = message;
        this.path = path;
        this.timestamp = System.currentTimeMillis();
    }
}
