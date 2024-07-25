package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Result;
import com.rehe.modules.admin.system.dto.reqeust.MenuCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuQueryDto;
import com.rehe.modules.admin.system.dto.reqeust.MenuUpdateDto;
import com.rehe.modules.admin.system.dto.response.MenuResponseDto;
import com.rehe.modules.admin.system.mapstruct.MenuMapstruct;
import com.rehe.modules.admin.system.service.MenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/6/26
 */
@Tag(name = "菜单管理",description = "菜单管理1")
@ApiSupport(order = 20)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/menu")
public class MenuController {
    private final MenuService menuService;

    @Operation(summary = "添加菜单",operationId = "1")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid MenuCreateDto menuCreateDto){
        menuService.createMenu(menuCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改菜单",operationId = "3")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid MenuUpdateDto menuUpdateDto){
        menuService.updateMenu(menuUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除菜单",operationId = "5")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "菜单ID") @PathVariable Long id){
        menuService.deleteMenu(id);
        return Result.ok();
    }

    @Operation(summary = "菜单列表",operationId = "10")
    @GetMapping("/query")
    public Result<List<MenuResponseDto>> query(@ParameterObject @Valid MenuQueryDto queryDto){
        List<MenuResponseDto> menuVoList = MenuMapstruct.INSTANCE.toMenuResponseDto(menuService.queryMenu(queryDto));
        return Result.ok(menuVoList);
    }


    @Operation(summary = "菜单详情",operationId = "13")
    @GetMapping("/get/{id}")
    public Result<MenuResponseDto> getById(@Parameter(description = "菜单ID") @PathVariable Long id){
        MenuResponseDto menuVo = MenuMapstruct.INSTANCE.toMenuResponseDto(menuService.getMenuById(id));
        return Result.ok(menuVo);
    }

}
