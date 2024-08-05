package com.rehe.modules.admin.system.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.DictDto;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.*;
import com.rehe.modules.admin.system.dto.response.DeptResponseDto;
import com.rehe.modules.admin.system.dto.response.DictDetailResponseDto;
import com.rehe.modules.admin.system.dto.response.DictResponseDto;
import com.rehe.modules.admin.system.mapstruct.DeptMapstruct;
import com.rehe.modules.admin.system.mapstruct.DictDetailMapstruct;
import com.rehe.modules.admin.system.mapstruct.DictMapstruct;
import com.rehe.modules.admin.system.service.DeptService;
import com.rehe.modules.admin.system.service.DictDetailService;
import com.rehe.modules.admin.system.service.DictService;
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
@Tag(name = "字典管理")
@ApiSupport(order = 100)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/dict")
public class DictController {
    private final DictService dictService;
    private final DictDetailService dictDetailService;

    @Operation(summary = "创建字典类型", operationId = "1")
    @PreAuthorize("hasAuthority('dict:create')")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid DictCreateDto dictCreateDto){
        dictService.createDict(dictCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改字典类型", operationId = "4")
    @PreAuthorize("hasAuthority('dict:update')")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid DictUpdateDto dictUpdateDto){
        dictService.updateDict(dictUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除字典类型", operationId = "5")
    @PreAuthorize("hasAuthority('dict:delete')")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "字典类型ID") @PathVariable Long id){
        dictService.deleteDict(id);
        return Result.ok();
    }


    @Operation(summary = "字典类型列表", operationId = "10")
    @PreAuthorize("hasAuthority('dict')")
    @GetMapping("/query")
    public ResultPage<DictResponseDto> query(@ParameterObject PageParamDto pageParamDto){
        Page<DictDto> pageInfo = dictService.queryDict(pageParamDto);
        return ResultPage.ok(DictMapstruct.INSTANCE.toDictResponseDto(pageInfo));
    }


    @Operation(summary = "创建字典明细", operationId = "60")
    @PreAuthorize("hasAuthority('dict_detail:create')")
    @PostMapping("/detail/create")
    public Result<Void> createDetail(@RequestBody @Valid DictDetailCreateDto dictDetailCreateDto){
        dictDetailService.createDictDetail(dictDetailCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改字典明细", operationId = "62")
    @PreAuthorize("hasAuthority('dict_detail:update')")
    @PostMapping("/detail/update")
    public Result<Void> updateDetail(@RequestBody @Valid DictDetailUpdateDto dictDetailUpdateDto){
        dictDetailService.updateDictDetail(dictDetailUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除字典明细", operationId = "64")
    @PreAuthorize("hasAuthority('dict_detail:delete')")
    @PostMapping("/detail/delete/{id}")
    public Result<Void> deleteDetail(@Parameter(description = "字典明细ID") @PathVariable Long id){
        dictDetailService.deleteDictDetail(id);
        return Result.ok();
    }

    @Operation(summary = "字典明细列表", operationId = "66")
    @PreAuthorize("hasAuthority('dict_detail')")
    @GetMapping("/detail/query/{dictId}")
    public Result<List<DictDetailResponseDto>> queryDictDetail(@Parameter(description = "字典类型ID") @PathVariable Long dictId) {
        List<DictDetailResponseDto> dictDetailResponseDtoList = DictDetailMapstruct.INSTANCE.toDeptResponseDto(dictDetailService.queryDictDetail(dictId));
        return Result.ok(dictDetailResponseDtoList);
    }

}
