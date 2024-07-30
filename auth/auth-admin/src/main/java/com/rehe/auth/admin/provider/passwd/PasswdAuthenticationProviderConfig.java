package com.rehe.auth.admin.provider.passwd;

import com.rehe.auth.admin.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author xiech
 * @description
 * @date 2024/1/3
 */
@Configuration
@RequiredArgsConstructor
public class PasswdAuthenticationProviderConfig {

//    private final AuthUserService authUserService;

    private final CustomUserDetailsService customUserDetailsService;

//    @Bean
//    public UserDetailsService userDetailsService() {
//        return username -> authUserService.findByUsername(username).orElseThrow(
//                () -> new BadCredentialsException("")
//        );
//    }
    @Bean
    public AuthenticationProvider passwdAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
