package com.rehe.auth.admin.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.Date;

/**
 *配置类
 */
@Configuration
@RequiredArgsConstructor
public class SecurityHandler {

    /**
     * 登录验证失败处理
     */
    @Bean
    public AuthenticationEntryPoint authExceptionEntryPoint() {
        return (request, response, authException) -> {
            JSONObject rest = new JSONObject();
            rest.put("error", "401");
            rest.put("message", authException.getMessage());
            rest.put("path", request.getServletPath());
            rest.put("timestamp", String.valueOf(new Date().getTime()));
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(rest));
        };
    }

    /**
     * 访问授权失败处理
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ( request, response, accessDeniedException) -> {
            JSONObject rest = new JSONObject();
            rest.put("error", "401");
            rest.put("message", accessDeniedException.getMessage());
            rest.put("path", request.getServletPath());
            rest.put("timestamp", String.valueOf(new Date().getTime()));
            if("不允许访问".equals(accessDeniedException.getMessage())){
                response.setStatus(HttpServletResponse.SC_PAYMENT_REQUIRED);
            }else{
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(rest));
        };
    }

    /**
     * 退出处理器
     */
    @Bean
    public LogoutHandler logoutHandler(){
        return (request,  response, authentication) -> {
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
                return;
            }
            jwt = authHeader.substring(7);
//        var storedToken = tokenRepository.findByToken(jwt)
//                .orElse(null);
//        if (storedToken != null) {
//            storedToken.setExpired(true);
//            storedToken.setRevoked(true);
//            tokenRepository.save(storedToken);
//            SecurityContextHolder.clearContext();
//        }
            SecurityContextHolder.clearContext();
        };
    }
}
