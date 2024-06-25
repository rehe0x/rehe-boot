package com.rehe.auth.admin.provider.mobile;

import com.rehe.auth.admin.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

/**
 * @author xiech
 * @description
 * @date 2024/1/3
 */
@Configuration
@RequiredArgsConstructor
public class MobileAuthenticationProviderConfig {


//    private UserTestMapper userTestMapper;
//    @Bean
    public UserDetailsService userDetailsService() {
//        return (UserDetailsService) new User();
        return sdf -> sg(sdf).orElseThrow();
    }


    public Optional<AuthUser> sg(String sdf){
        return Optional.of(new AuthUser());
    }

    @Bean
    public AuthenticationProvider mobileAuthenticationProvider() {
        MobileAuthenticationProvider authProvider = new MobileAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        userTestMapper.test();
        return authProvider;
    }
}
