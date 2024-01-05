package com.rehe.controller.admin.security.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final PasswordEncoder passwordEncoder;
//
//    @PostMapping("/register")
//    public ResponseEntity<AuthenticationResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ResponseEntity.ok(service.register(request));
//    }
    @GetMapping("/authenticate")
    public ResponseEntity<String> authenticate() {
        String s = passwordEncoder.encode("123123");
        System.out.println(s);
        return ResponseEntity.ok(service.authenticate());
    }
    @GetMapping("/authenticate1")
    public ResponseEntity<String> authenticate1() {
        String s = passwordEncoder.encode("123123");
        System.out.println(s);
        return ResponseEntity.ok(service.authenticate1());
    }
    @GetMapping("/authenticate2")
    public ResponseEntity<String> authenticate2() {
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
