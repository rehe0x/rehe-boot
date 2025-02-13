package com.rehe.scheduled.modules.task.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/***
 * @description
 * @author rehe
 * @date 2025/2/12
 */
@Schema(description = "定时任务查询")
@Data
public class ScheduledTaskQueryDto {
    @Schema(description = "关键字")
    private String keyword;
}
