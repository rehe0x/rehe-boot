package com.rehe.auth.admin.controller;

import com.rehe.auth.admin.dto.AdminLoginDto;
import com.rehe.auth.admin.service.AuthenticationService;
import com.rehe.common.exception.BusinessException;
import com.rehe.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api1/v1/auth")
@RequiredArgsConstructor
@Tag(name = "222")
public class testController {

    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "222",operationId = "1")
    @GetMapping("/test")
//    @PreAuthorize("hasAuthority('admin:read')")
    public Result<String> loginPasswd() {

        System.out.println(1);
//        throw new BusinessException("错误111");
        return Result.ok("sdf");
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
