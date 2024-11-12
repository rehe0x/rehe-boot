package com.rehe.admin.modules.logging.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.admin.common.dto.PageParamDto;
import com.rehe.admin.modules.logging.dto.OperationLogDto;
import com.rehe.admin.modules.logging.dto.reqeust.OperationLogQueryDto;
import com.rehe.admin.modules.logging.dto.response.OperationLogResonseDto;
import com.rehe.admin.modules.logging.mapstruct.OperationLogMapstruct;
import com.rehe.admin.modules.logging.service.OperationLogService;
import com.rehe.admin.modules.system.dto.UserDto;
import com.rehe.admin.modules.system.dto.reqeust.UserCreateDto;
import com.rehe.admin.modules.system.dto.reqeust.UserQueryDto;
import com.rehe.admin.modules.system.dto.reqeust.UserUpdateDto;
import com.rehe.admin.modules.system.dto.response.UserResponseDto;
import com.rehe.admin.modules.system.mapstruct.UserMapstruct;
import com.rehe.admin.modules.system.service.UserService;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.common.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @description
 * @author rehe
 * @date 2024/11/12
 */
@Tag(name = "日志管理")
@ApiSupport(order = 100)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/operation/log")
public class OperationLogController {
    private final OperationLogService operationLogService;


    @Operation(summary = "日志列表", operationId = "10")
    @PreAuthorize("hasAuthority('operationlog')")
    @GetMapping("/query")
    public ResultPage<OperationLogResonseDto> query(@ParameterObject @Valid OperationLogQueryDto operationLogQueryDto,
                                             @ParameterObject PageParamDto pageParamDto) {
        Page<OperationLogDto> pageInfo = operationLogService.queryOperationLog(operationLogQueryDto, pageParamDto);
        return ResultPage.ok(OperationLogMapstruct.INSTANCE.toOperationLogResponseDto(pageInfo));
    }

    @Operation(summary = "日志详情", operationId = "12")
    @PreAuthorize("hasAuthority('operationlog')")
    @GetMapping("/get/{id}")
    public Result<OperationLogResonseDto> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        OperationLogResonseDto operationLogResonseDto = OperationLogMapstruct.INSTANCE.toOperationLogResponseDto(operationLogService.getOperationLogById(id));
        return Result.ok(operationLogResonseDto);
    }

}
