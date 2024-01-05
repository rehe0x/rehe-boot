//package com.rehe.controller.admin.security.config;
//
//import com.rehe.controller.admin.security.provider.openid.OpenIdAuthenticationProvider;
//import com.rehe.controller.admin.security.user.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import java.util.Optional;
//
//@Configuration
//@RequiredArgsConstructor
//public class ApplicationConfig {
//    @Bean
//    public UserDetailsService userDetailsService() {
////        return (UserDetailsService) new User();
//        return sdf -> sg(sdf).orElseThrow();
//    }
//
//
//    public  Optional<User> sg(String sdf){
//        return Optional.of(new User(1,"test","$2a$10$9VZMQJyzcN0NI.j/eOsWZeg0Fke/iPvuewYEqhCHxqeeg2v16Jqs2"));
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProviderf() {
//        OpenIdAuthenticationProvider authProvider = new OpenIdAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailsService());
//        return authProvider;
//    }
////
////    @Bean
////    public AuditorAware<Integer> auditorAware() {
////        return new ApplicationAuditAware();
////    }
//
//
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
