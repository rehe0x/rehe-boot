package com.rehe.admin.modules.logging.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @description 日志记录
 * @author rehe
 * @date 2024/11/11
 */
@Schema(description="日志对象")
@Data
public class OperationLogResonseDto implements Serializable {
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