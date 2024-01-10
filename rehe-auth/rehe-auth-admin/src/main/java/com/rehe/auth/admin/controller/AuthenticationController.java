package com.rehe.auth.admin.controller;

import com.rehe.auth.admin.dto.AdminLoginDto;
import com.rehe.auth.admin.service.AuthenticationService;
import com.rehe.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Auth管理")
public class AuthenticationController {

    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "账号密码登录",operationId = "1")
    @GetMapping("/login/passwd")
    public Result<String> loginPasswd(@ModelAttribute  AdminLoginDto adminLoginDto) {
        String s = passwordEncoder.encode("123123");
        System.out.println(s);
        return Result.ok(service.authPasswd(adminLoginDto));
    }

    @Operation(summary = "手机验证码登录",operationId = "1")
    @GetMapping("/login/mobile")
    public ResponseEntity<String> loginMobile(AdminLoginDto adminLoginDto) {
        String s = passwordEncoder.encode("123123");
        System.out.println(s);
        return ResponseEntity.ok(service.authPasswd(adminLoginDto));
    }

    @Operation(summary = "微信登录",operationId = "1")
    @GetMapping("/login/wx")
    public Result<String> loginWx(AdminLoginDto adminLoginDto) {
        return Result.ok(service.authOpenId("asdf",null));
    }

//    @Operation(summary = "获取登录信息",operationId = "2")
//    @GetMapping("/info")
//    public ResponseEntity<String> loginInfo() {
//        String s = passwordEncoder.encode("123123");
//        System.out.println(s);
//        return ResponseEntity.ok(service.authenticate1());
//    }

    @Operation(summary = "退出登录",operationId = "3")
    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
//        List s1 = adminUserMapper.test();
//        System.out.println(s1.get(0).toString());
//
//        List s2 = appUserMapper.test();
//        System.out.println(s2.get(0).toString());
//
//        service.test1();
        String s = passwordEncoder.encode("123123");
        System.out.println(s);
        return ResponseEntity.ok(service.authenticate2());
    }
//
//    @PostMapping("/refresh-token")
//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        service.refreshToken(request, response);
//    }
}
