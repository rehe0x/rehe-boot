package com.rehe.auth.admin.auth;

import com.rehe.auth.admin.config.JwtService;
import com.rehe.auth.admin.mapper.UserTestMapper;
import com.rehe.auth.admin.provider.mobile.MobileAuthenticationToken;
import com.rehe.auth.admin.provider.openid.OpenIdAuthenticationToken;
import com.rehe.auth.admin.test.mapper.AppUserMapper;
import com.rehe.auth.admin.user.User;
import com.rehe.common.db.DBSource;
import com.rehe.common.db.DynamicDataSourceEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceTest {


    private final UserTestMapper userTestMapper;
    private final AppUserMapper appUserMapper;

    @DBSource
    public void test2(){
        System.out.println("3333333");
        List s2 = appUserMapper.test();
        System.out.println(s2.get(0).toString());
    }
}
