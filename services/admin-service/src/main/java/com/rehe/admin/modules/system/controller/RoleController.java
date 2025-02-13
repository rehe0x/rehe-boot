package com.rehe.admin.modules.system.controller;

import com.github.xingfudeshi.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.biz.core.common.dto.PageParamDto;
import com.rehe.admin.modules.system.dto.reqeust.*;
import com.rehe.admin.modules.system.dto.response.DeptResponseDto;
import com.rehe.admin.modules.system.dto.response.RoleResponseDto;
import com.rehe.admin.modules.system.mapstruct.DeptMapstruct;
import com.rehe.admin.modules.system.mapstruct.RoleMapstruct;
import com.rehe.admin.modules.system.service.DeptService;
import com.rehe.admin.modules.system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAuthority('role:create')")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid RoleCreateDto roleCreateDto){
        roleService.createRole(roleCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改角色", operationId = "4")
    @PreAuthorize("hasAuthority('role:update')")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid RoleUpdateDto roleUpdateDto){
        roleService.updateRole(roleUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除角色", operationId = "5")
    @PreAuthorize("hasAuthority('role:delete')")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "角色ID") @PathVariable Long id){
        roleService.deleteRole(id);
        return Result.ok();
    }

    @Operation(summary = "角色绑定菜单", operationId = "5")
    @PreAuthorize("hasAnyAuthority('role:bind_menu')")
    @PostMapping("/bind/menu")
    public Result<Void> bindRoleMenu(@RequestBody @Valid RoleMenuBindDto roleMenuBindDto){
        roleService.bindRoleMenu(roleMenuBindDto);
        return Result.ok();
    }

    @Operation(summary = "角色详情", operationId = "8")
    @PreAuthorize("hasAuthority('role')")
    @GetMapping("/get/{id}")
    public Result<RoleResponseDto> getById(@Parameter(description = "部门ID") @PathVariable Long id) {
        RoleResponseDto roleResponseDto = RoleMapstruct.INSTANCE.toRoleResponseDto(roleService.getRoleById(id));
        return Result.ok(roleResponseDto);
    }

    @Operation(summary = "角色分页列表", operationId = "10")
    @PreAuthorize("hasAuthority('role')")
    @GetMapping("/query")
    public ResultPage<RoleResponseDto> query(@ParameterObject @Valid RoleQueryDto roleQueryDto,
                                                   @ParameterObject PageParamDto pageParamDto){
        Page<RoleResponseDto> roleResponseDtoPage =
                RoleMapstruct.INSTANCE.toRoleResponseDto(roleService.queryRole(roleQueryDto, pageParamDto));
        return ResultPage.ok(roleResponseDtoPage);
    }

    @Operation(summary = "角色全部列表", operationId = "11")
    @GetMapping("/query/all")
    public Result<List<RoleResponseDto>> queryAll(){
        List<RoleResponseDto> roleResponseDtoList =
                RoleMapstruct.INSTANCE.toRoleResponseDto(roleService.queryRole());
        return ResultPage.ok(roleResponseDtoList);
    }
}
