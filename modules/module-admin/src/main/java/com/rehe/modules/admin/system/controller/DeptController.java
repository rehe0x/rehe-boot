package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Result;
import com.rehe.modules.admin.system.dto.DeptCreateDto;
import com.rehe.modules.admin.system.dto.DeptUpdateDto;
import com.rehe.modules.admin.system.service.DeptService;
import com.rehe.modules.admin.system.vo.DeptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid DeptCreateDto deptCreateDto){
        deptService.createDept(deptCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改部门", operationId = "4")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Valid DeptUpdateDto deptUpdateDto){
        deptService.updateDept(deptUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除部门", operationId = "5")
    @PostMapping("delete/{id}")
    public Result<Void> delete(@Parameter(description = "部门ID") @PathVariable Long id){
        deptService.deleteDept(id);
        return Result.ok();
    }

    @Operation(summary = "部门详情", operationId = "6")
    @GetMapping("/get/{id}")
    public Result<DeptVo> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        DeptVo deptVo = deptService.getDeptById(id);
        return Result.ok(deptVo);
    }

    @Operation(summary = "部门列表", operationId = "10")
    @GetMapping("/query")
    public Result<List<DeptVo>> query(){
        List<DeptVo> deptVoList = deptService.queryDept();
        return Result.ok(deptVoList);
    }
}
