package com.rehe.admin.modules.logging.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;
/***
 * @description 日志记录
 * @author rehe
 * @date 2024/11/11
 */
@Data
public class OperationLog implements Serializable {
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

    private static final long serialVersionUID = 1L;
}