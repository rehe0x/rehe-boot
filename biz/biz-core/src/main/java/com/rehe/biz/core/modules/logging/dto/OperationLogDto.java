package com.rehe.biz.core.modules.logging.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @description 日志记录
 * @author rehe
 * @date 2024/11/11
 */
@Data
public class OperationLogDto implements Serializable {
    private Long id;

    private String username;

    private String description;

    private String logType;

    private String method;

    private String params;

    private String ip;

    private String address;

    private String header;

    private Long time;

    private String detail;

    private LocalDateTime createTime;

    @Serial
    private static final long serialVersionUID = 1L;
}