package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Result;
import com.rehe.modules.admin.system.dto.reqeust.*;
import com.rehe.modules.admin.system.dto.response.DeptResponseDto;
import com.rehe.modules.admin.system.dto.response.RoleResponseDto;
import com.rehe.modules.admin.system.mapstruct.DeptMapstruct;
import com.rehe.modules.admin.system.mapstruct.RoleMapstruct;
import com.rehe.modules.admin.system.service.DeptService;
import com.rehe.modules.admin.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@Tag(name = "角色管理")
@ApiSupport(order = 60)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/role")
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "创建角色", operationId = "1")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid RoleCreateDto roleCreateDto){
        roleService.createRole(roleCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改角色", operationId = "4")
    @PostMapping("update")
    public Result<Void> update(@RequestBody @Valid RoleUpdateDto roleUpdateDto){
        roleService.updateRole(roleUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除角色", operationId = "5")
    @PostMapping("delete/{id}")
    public Result<Void> delete(@Parameter(description = "角色ID") @PathVariable Long id){
        roleService.deleteRole(id);
        return Result.ok();
    }

    @Operation(summary = "角色绑定菜单权限", operationId = "5")
    @PostMapping("bind/menu")
    public Result<Void> bindRoleMenu(@RequestBody @Valid RoleMenuBindDto roleMenuBindDto){
        roleService.bindRoleMenu(roleMenuBindDto);
        return Result.ok();
    }

    @Operation(summary = "角色详情", operationId = "8")
    @GetMapping("/get/{id}")
    public Result<RoleResponseDto> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        RoleResponseDto roleResponseDto = RoleMapstruct.INSTANCE.toRoleResponseDto(roleService.getRoleById(id));
        return Result.ok(roleResponseDto);
    }

    @Operation(summary = "角色列表", operationId = "10")
    @GetMapping("/query")
    public Result<List<RoleResponseDto>> query(){
        List<RoleResponseDto> deptVoList = RoleMapstruct.INSTANCE.toRoleResponseDto(roleService.queryRole());
        return Result.ok(deptVoList);
    }
}
