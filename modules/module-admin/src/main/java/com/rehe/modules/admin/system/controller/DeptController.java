package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Result;
import com.rehe.modules.admin.system.dto.reqeust.DeptCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.DeptUpdateDto;
import com.rehe.modules.admin.system.dto.response.DeptResponseDto;
import com.rehe.modules.admin.system.mapstruct.DeptMapstruct;
import com.rehe.modules.admin.system.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/7/8
 */
@Tag(name = "部门管理")
@ApiSupport(order = 30)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/dept")
public class DeptController {
    private final DeptService deptService;

    @Operation(summary = "创建部门", operationId = "1")
    @PreAuthorize("hasAuthority('dept:create')")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid DeptCreateDto deptCreateDto){
        deptService.createDept(deptCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改部门", operationId = "4")
    @PreAuthorize("hasAuthority('dept:update')")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Valid DeptUpdateDto deptUpdateDto){
        deptService.updateDept(deptUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除部门", operationId = "5")
    @PreAuthorize("hasAuthority('dept:delete')")
    @PostMapping("delete/{id}")
    public Result<Void> delete(@Parameter(description = "部门ID") @PathVariable Long id){
        deptService.deleteDept(id);
        return Result.ok();
    }

    @Operation(summary = "部门详情", operationId = "6")
    @PreAuthorize("hasAuthority('dept')")
    @GetMapping("/get/{id}")
    public Result<DeptResponseDto> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        DeptResponseDto deptVo = DeptMapstruct.INSTANCE.toDeptResponseDto(deptService.getDeptById(id));
        return Result.ok(deptVo);
    }

    @Operation(summary = "部门列表", operationId = "10")
    @PreAuthorize("hasAuthority('dept')")
    @GetMapping("/query")
    public Result<List<DeptResponseDto>> query(){
        List<DeptResponseDto> deptVoList = DeptMapstruct.INSTANCE.toDeptResponseDto(deptService.queryDept());
        return Result.ok(deptVoList);
    }
}
