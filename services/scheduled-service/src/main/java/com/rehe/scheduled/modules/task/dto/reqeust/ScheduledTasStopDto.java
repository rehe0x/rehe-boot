package com.rehe.scheduled.modules.task.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serializable;

/***
 * @description
 * @author rehe
 * @date 2025/2/12
 */
@Schema(description = "暂停定时任务")
@Data
public class ScheduledTasStopDto implements Serializable {


    @Schema(description = "任务ID")
    @NotNull(message = "任务ID不能为空")
    private Long id;

    @Schema(description = "任务状态1正常 0暂停")
    @NotNull(message = "任务状态不能为空")
    @Max(value = 1,message = "任务状态错误")
    @Min(value = 0,message = "任务状态错误")
    private Short status;

}