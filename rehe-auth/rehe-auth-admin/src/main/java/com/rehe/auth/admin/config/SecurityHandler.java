package com.rehe.auth.admin.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.rehe.common.result.HttpError;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
            // 无效token 401
            HttpError httpError = new HttpError(HttpStatus.UNAUTHORIZED.value(),HttpStatus.UNAUTHORIZED.getReasonPhrase(),request.getServletPath());
            response.setStatus(httpError.getError());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(httpError));
        };
    }

    /**
     * 访问授权失败处理
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ( request, response, accessDeniedException) -> {
            // 无权限 403
            HttpError httpError = new HttpError(HttpStatus.FORBIDDEN.value(),HttpStatus.FORBIDDEN.getReasonPhrase(),request.getServletPath());
            response.setStatus(httpError.getError());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(httpError));
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
