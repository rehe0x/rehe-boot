package com.rehe.auth.admin.provider.openid;

import com.rehe.auth.admin.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

/**
 * @author xiech
 * @description
 * @date 2024/1/3
 */
@Configuration
@RequiredArgsConstructor
public class OpenIdAuthenticationProviderConfig {

    private final CustomUserDetailsService customUserDetailsService;

    @Bean
    public AuthenticationProvider openIdAuthenticationProvider() {
        OpenIdAuthenticationProvider authProvider = new OpenIdAuthenticationProvider();
        authProvider.setCustomUserDetailsService(customUserDetailsService);
        return authProvider;
    }
}
