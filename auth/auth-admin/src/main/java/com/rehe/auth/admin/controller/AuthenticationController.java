package com.rehe.auth.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.auth.admin.dto.request.AdminLoginDto;
import com.rehe.auth.admin.dto.request.AdminWxLoginDto;
import com.rehe.auth.admin.dto.response.AuthUserResponseDto;
import com.rehe.auth.admin.service.AuthenticationService;
import com.rehe.auth.admin.dto.response.AuthTokenResponseDto;
import com.rehe.common.result.Result;
import com.rehe.common.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
/***
 * @description
 * @author rehe
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth管理",description = "Auth管理")
@ApiSupport(order = 20)
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "账号密码登录",operationId = "1")
    @GetMapping("/login/passwd")
    public Result<AuthTokenResponseDto> loginPasswd(@ParameterObject @Valid AdminLoginDto adminLoginDto) {
        return Result.ok(AuthTokenResponseDto.builder().token(service.authPasswd(adminLoginDto)).refreshToken("is null").build());
    }

    @Operation(summary = "手机验证码登录",operationId = "2")
    @GetMapping("/login/mobile")
    public Result<AuthTokenResponseDto> loginMobile(@Parameter(description = "手机号") @NotBlank(message = "手机号不能为空") String phone,
                                                    @Parameter(description = "验证码") @NotBlank(message = "验证码不能为空") String code) {
        return Result.ok(AuthTokenResponseDto.builder().token(service.authMobile(phone, code)).refreshToken("is null").build());

    }

    @Operation(summary = "微信登录",operationId = "3", description = "openId和code二选一")
    @GetMapping("/login/wx")
    public Result<AuthTokenResponseDto> loginWx(@ParameterObject @Valid AdminWxLoginDto adminWxLoginDto) {
        return Result.ok(AuthTokenResponseDto.builder().token(service.authOpenId(adminWxLoginDto.getCode(),adminWxLoginDto.getOpenId())).refreshToken("is null").build());
    }


    @Operation(summary = "获取登录信息",operationId = "4")
    @GetMapping("/info")
    public Result<AuthUserResponseDto> loginInfo(Integer platformId) {
        Long userId = SecurityUtils.getLoginUserId();
        return Result.ok(service.userInfo(platformId, userId));
    }
}
