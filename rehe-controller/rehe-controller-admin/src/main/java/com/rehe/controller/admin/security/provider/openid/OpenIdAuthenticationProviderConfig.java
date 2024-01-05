package com.rehe.controller.admin.security.provider.openid;

import com.rehe.controller.admin.security.user.User;
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
public class OpenIdAuthenticationProviderConfig {
//    @Bean
    public UserDetailsService userDetailsService() {
//        return (UserDetailsService) new User();
        return sdf -> sg(sdf).orElseThrow();
    }


    public Optional<User> sg(String sdf){
        return Optional.of(new User(1,"test","$2a$10$9VZMQJyzcN0NI.j/eOsWZeg0Fke/iPvuewYEqhCHxqeeg2v16Jqs2"));
    }

    @Bean
    public AuthenticationProvider openIdAuthenticationProvider() {
        OpenIdAuthenticationProvider authProvider = new OpenIdAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        return authProvider;
    }
}
