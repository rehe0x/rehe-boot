package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Result;
import com.rehe.modules.admin.system.service.DeptService;
import com.rehe.modules.admin.system.vo.DeptVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "部门列表", operationId = "10")
    @GetMapping("/query")
    public Result<List<DeptVo>> query(){
        List<DeptVo> deptVoList = deptService.queryDepts();
        return Result.ok(deptVoList);
    }
}
