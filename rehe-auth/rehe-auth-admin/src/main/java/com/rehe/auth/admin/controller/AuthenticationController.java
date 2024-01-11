package com.rehe.auth.admin.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.auth.admin.dto.AdminLoginDto;
import com.rehe.auth.admin.dto.AdminWxLoginDto;
import com.rehe.auth.admin.service.AuthenticationService;
import com.rehe.auth.admin.vo.AuthTokenVo;
import com.rehe.auth.admin.vo.AuthUserInfoVo;
import com.rehe.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth管理",description = "Auth管理")
@ApiSupport(order = 20)
public class AuthenticationController {

    private final AuthenticationService service;

    @Operation(summary = "账号密码登录",operationId = "1")
    @GetMapping("/login/passwd")
    public Result<AuthTokenVo> loginPasswd(@ParameterObject @Valid AdminLoginDto adminLoginDto) {
        return Result.ok(AuthTokenVo.builder().token(service.authPasswd(adminLoginDto)).refreshToken("is null").build());
    }

    @Operation(summary = "手机验证码登录",operationId = "2")
    @GetMapping("/login/mobile")
    public Result<AuthTokenVo> loginMobile(@Parameter(description = "手机号") @NotBlank(message = "手机号不能为空") String phone,
                                           @Parameter(description = "验证码") @NotBlank(message = "验证码不能为空") String code) {
        return Result.ok(AuthTokenVo.builder().token(service.authMobile(phone, code)).refreshToken("is null").build());

    }

    @Operation(summary = "微信登录",operationId = "3", description = "openId和code二选一")
    @GetMapping("/login/wx")
    public Result<AuthTokenVo> loginWx(@ParameterObject @Valid AdminWxLoginDto adminWxLoginDto) {
        return Result.ok(AuthTokenVo.builder().token(service.authOpenId(adminWxLoginDto.getCode(),adminWxLoginDto.getOpenId())).refreshToken("is null").build());
    }


    @Operation(summary = "获取登录信息",operationId = "4")
    @GetMapping("/info")
    public Result<AuthUserInfoVo> loginInfo() {
        return Result.ok(service.userInfo());
    }
}
