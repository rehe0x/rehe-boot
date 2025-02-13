package com.rehe.scheduled.modules.task.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/***
 * @description
 * @author rehe
 * @date 2025/2/12
 */
@Schema(description = "修改定时任务")
@Data
public class ScheduledTaskUpdateDto implements Serializable {

    @NotNull(message = "ID不能为空")
    private Long id;

    @Schema(description = "任务名称")
    @NotBlank(message = "任务名称不能为空")
    private String taskName;

    @Schema(description = "实例名称")
    @NotBlank(message = "实例名称不能为空")
    private String beanName;

    @Schema(description = "方法名称")
    @NotBlank(message = "方法名称不能为空")
    private String methodName;

    @Schema(description = "参数")
    private String argument;

    @Schema(description = "子任务taskId多个逗号分隔")
    private String subTask;

    @Schema(description = "任务方式 1正则表达式 2固定时间间隔 3固定延迟")
    @NotNull(message = "任务方式不能为空")
    private Short taskMode;

    @Schema(description = "表达式 Cron/间隔时间")
    @NotBlank(message = "表达式不能为空")
    private String expression;

    @Schema(description = "描述")
    private String description;

}