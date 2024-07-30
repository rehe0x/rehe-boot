package com.rehe.auth.admin.config;

import com.alibaba.fastjson2.JSONObject;
import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/**
 * @description
 * @author rehe
 * @date 2024/7/29
 */
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        if(StringUtils.isEmpty(jwt) || jwt.length() < 20){
            filterChain.doFilter(request, response);
            return;
        }
        // 解析token用户信息
        Claims claims = jwtService.extractAllClaims(jwt);
        if (StringUtils.isNotBlank(claims.getSubject()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            // 根据token设置SecurityContext上下文 解决方案
            // 1 解析token用户id查询数据
            // 2 用户信息存redis查询redis
            // 3 用户信息存token包括权限直接解析token 目前采用这种
            JwtUserDto userDetails = JSONObject.parseObject(JSONObject.toJSONString(claims), JwtUserDto.class);
//            AuthUserDto userDetails = (AuthUserDto) this.userDetailsService.loadUserByUsername(username);
            if (jwtService.isTokenValid(claims, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
