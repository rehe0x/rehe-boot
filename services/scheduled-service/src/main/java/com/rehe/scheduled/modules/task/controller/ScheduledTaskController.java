package com.rehe.scheduled.modules.task.controller;

import com.github.xingfudeshi.knife4j.annotations.ApiSupport;
import com.rehe.biz.core.common.dto.PageParamDto;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.common.util.SecurityUtils;
import com.rehe.scheduled.modules.task.dto.ScheduledTaskDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTasStopDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskCreateDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskQueryDto;
import com.rehe.scheduled.modules.task.dto.reqeust.ScheduledTaskUpdateDto;
import com.rehe.scheduled.modules.task.dto.response.ScheduledTaskResonseDto;
import com.rehe.scheduled.modules.task.mapstruct.ScheduledTaskMapstruct;
import com.rehe.scheduled.modules.task.service.ScheduledTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/***
 * @description
 * @author rehe
 * @date 2025/2/12
 */
@Tag(name = "定时任务")
@ApiSupport(order = 55)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/task")
public class ScheduledTaskController {

    private final ScheduledTaskService scheduledTaskService;

    @Operation(summary = "添加定时任务",operationId = "1")
    @PreAuthorize("hasAuthority('task:create')")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid ScheduledTaskCreateDto scheduledTaskCreateDto) {
        scheduledTaskService.createScheduledTask(scheduledTaskCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改定时任务", operationId = "3")
    @PreAuthorize("hasAuthority('task:update')")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid ScheduledTaskUpdateDto scheduledTaskUpdateDto) {
        scheduledTaskService.updateScheduledTask(scheduledTaskUpdateDto);
        return Result.ok();
    }


    @Operation(summary = "删除定时任务", operationId = "6")
    @PreAuthorize("hasAuthority('task:delete')")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "任务ID") @PathVariable Long id) {
        scheduledTaskService.deleteScheduledTask(id);
        return Result.ok();
    }

    @Operation(summary = "暂停/启动任务", operationId = "7")
    @PreAuthorize("hasAuthority('task:stop')")
    @PostMapping("/stop")
    public Result<Void> stop(@RequestBody @Valid ScheduledTasStopDto scheduledTasStopDto) {
        scheduledTaskService.stopStartScheduledTask(scheduledTasStopDto.getId(), scheduledTasStopDto.getStatus());
        return Result.ok();
    }

    @Operation(summary = "立即执行任务", operationId = "8")
    @PreAuthorize("hasAuthority('task:execute')")
    @PostMapping("/execute/{id}")
    public Result<Void> execute(@Parameter(description = "任务ID") @PathVariable Long id) {
        scheduledTaskService.executeTask(id);
        return Result.ok();
    }

    @Operation(summary = "重新加载所有任务", operationId = "9")
    @PreAuthorize("hasAuthority('task:reload')")
    @PostMapping("/reload")
    public Result<Void> reload() {
        scheduledTaskService.reloadTask();
        return Result.ok();
    }

    @Operation(summary = "定时任务列表", operationId = "10")
    @PreAuthorize("hasAuthority('task')")
    @GetMapping("/query")
    public ResultPage<ScheduledTaskResonseDto> query(@ParameterObject @Valid ScheduledTaskQueryDto scheduledTaskQueryDto,
            @ParameterObject PageParamDto pageParamDto) {
        Page<ScheduledTaskDto> pageInfo = scheduledTaskService.queryScheduledTask(scheduledTaskQueryDto, pageParamDto);
        return ResultPage.ok(ScheduledTaskMapstruct.INSTANCE.toScheduledTaskResponseDto(pageInfo));
    }

    @Operation(summary = "定时任务详情", operationId = "12")
    @PreAuthorize("hasAuthority('task')")
    @GetMapping("/get/{id}")
    public Result<ScheduledTaskResonseDto> getById(@Parameter(description = "任务ID") @PathVariable Long id) {
        ScheduledTaskResonseDto scheduledTaskResonseDto = ScheduledTaskMapstruct.INSTANCE.toScheduledTaskResponseDto(scheduledTaskService.getScheduledTaskById(id));
        return Result.ok(scheduledTaskResonseDto);
    }
}
