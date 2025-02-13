package com.rehe.auth.admin.controller;

import com.github.xingfudeshi.knife4j.annotations.ApiSupport;
import com.rehe.auth.admin.dto.request.ApiDemo2Dto;
import com.rehe.auth.admin.dto.request.ApiDemoDto;
import com.rehe.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
@Tag(name = "接口示例", description = "接口示例")
@ApiSupport(order = 10)
public class testController {
    @Operation(summary = "Get请求,对象参数",operationId = "1", description = "用@ParameterObject注解")
    @GetMapping("/get_v1")
    public Result<ApiDemoDto> get_v1(@ParameterObject @Valid ApiDemoDto apiDemoDto) {
        return Result.ok(apiDemoDto);
    }

    @Operation(summary = "Get请求,对象参数二选一",operationId = "3", description = "openId和code二选一")
    @GetMapping("/get_v2")
    public Result<ApiDemo2Dto> loginWx(@ParameterObject @Valid ApiDemo2Dto apiDemo2Dto) {
        return Result.ok(apiDemo2Dto);
    }

    @Operation(summary = "Get请求,单个参数",operationId = "4", description = "用@Parameter注释，@NotBlank验证")
    @GetMapping("/get_v3")
    public ResponseEntity<String> get_v2(@Parameter(description = "手机号") @NotBlank(message = "手机号不能为空") String phone,
                                              @Parameter(description = "验证码") @NotBlank(message = "验证码不能为空") String code) {
        return ResponseEntity.ok(phone+"--"+code);
    }



    @Operation(summary = "path参数,单个参数",operationId = "44", description = "用@PathVariable 适合单个参数post或get")
    @GetMapping("/path/{id}")
    public Result<String> path(@Parameter(description = "用户id") @PathVariable String id) {
        return Result.ok(id);
    }

    @Operation(summary = "post请求,对象参数json",operationId = "55", description = "用@RequestBody")
    @PostMapping("/post")
    public Result<ApiDemoDto> sdf(@Valid @RequestBody ApiDemoDto ApiDemoDto) {
        return Result.ok(ApiDemoDto);
    }
}
