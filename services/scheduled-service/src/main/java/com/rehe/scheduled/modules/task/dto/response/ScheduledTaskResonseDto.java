package com.rehe.scheduled.modules.task.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/***
 * @description
 * @author rehe
 * @date 2025/2/12
 */
@Schema(description = "定时任务返回DTO")
@Data
public class ScheduledTaskResonseDto implements Serializable {
    private Long id;

    @Schema(description = "任务ID")
    private String taskId;

    @Schema(description = "任务名称")
    private String taskName;

    @Schema(description = "实例名称")
    private String beanName;

    @Schema(description = "方法名称")
    private String methodName;

    @Schema(description = "参数")
    private String argument;

    @Schema(description = "子任务taskId多个逗号分隔")
    private String subTask;

    @Schema(description = "任务方式 1正则表达式 2固定时间间隔 3固定延迟")
    private Short taskMode;

    @Schema(description = "表达式 Cron/间隔时间")
    private String expression;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "任务状态1正常 0暂停")
    private Short status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}