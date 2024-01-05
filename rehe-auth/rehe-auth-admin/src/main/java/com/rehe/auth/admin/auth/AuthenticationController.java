package com.rehe.auth.admin.auth;

import com.rehe.auth.admin.mapper.UserTestMapper;
import com.rehe.auth.admin.test.mapper.AppUserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final UserTestMapper userTestMapper;
    private final AppUserMapper appUserMapper;

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
        List s1 = userTestMapper.test();
        System.out.println(s1.get(0).toString());

        List s2 = appUserMapper.test();
        System.out.println(s2.get(0).toString());

        service.test1();
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
