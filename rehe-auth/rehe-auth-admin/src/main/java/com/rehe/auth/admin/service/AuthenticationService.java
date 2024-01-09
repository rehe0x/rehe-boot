package com.rehe.auth.admin.service;

import com.alibaba.fastjson2.JSON;
import com.rehe.auth.admin.dto.AdminLoginDto;
import com.rehe.auth.admin.mapper.AuthUserMapper;
import com.rehe.auth.admin.provider.mobile.MobileAuthenticationToken;
import com.rehe.auth.admin.provider.openid.OpenIdAuthenticationToken;
import com.rehe.auth.admin.entity.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthUserService authUserService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public String authPasswd(AdminLoginDto adminLoginDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        adminLoginDto.getUsername(),
                        adminLoginDto.getPassword()
                )
        );
        AuthUser authUser = authUserService.findByUsername(adminLoginDto.getUsername());
        authUser.setPassword(null);
        // 用户基本信息存入token
        Map<String,Object> extraClaims = JSON.parseObject(JSON.toJSONString(authUser));
        return jwtService.generateToken(extraClaims, authUser);
    }

    /**
     *
     * @param openId openid和code二选一
     * @param code
     * @return
     */
    public String authOpenId(String openId,String code) {
        authenticationManager.authenticate(
                new OpenIdAuthenticationToken(openId)
        );


        AuthUser authUser = (AuthUser) authUserService.findByOpenId(openId);
        authUser.setPassword(null);
        // 用户基本信息存入token
        Map<String,Object> extraClaims = JSON.parseObject(JSON.toJSONString(authUser));
        return jwtService.generateToken(extraClaims, authUser);
    }

    public String authenticate2() {
        authenticationManager.authenticate(
                new MobileAuthenticationToken(
                        "test","sd"
                )
        );
//        var user = repository.findByEmail(request.getEmail())
//                .orElseThrow();
        String jwtToken = jwtService.generateToken(new AuthUser());
//        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return jwtToken;
    }

//    private void saveUserToken(User user, String jwtToken) {
//        var token = Token.builder()
//                .user(user)
//                .token(jwtToken)
//                .tokenType(TokenType.BEARER)
//                .expired(false)
//                .revoked(false)
//                .build();
//        tokenRepository.save(token);
//    }

//    private void revokeAllUserTokens(User user) {
//        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
//        if (validUserTokens.isEmpty())
//            return;
//        validUserTokens.forEach(token -> {
//            token.setExpired(true);
//            token.setRevoked(true);
//        });
//        tokenRepository.saveAll(validUserTokens);
//    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null ||!authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }

//    @DBSource(value = DynamicDataSourceEnum.SLAVE)
//    public void test1(){
//        System.out.println("========");
//        List s1 = adminUserMapper.test();
//        System.out.println(s1.get(0).toString());
//
//        List s2 = appUserMapper.test();
//        System.out.println(s2.get(0).toString());
//        authenticationServiceTest.test2();
//    }
//    @DBSource(value = DynamicDataSourceEnum.SLAVE)
//    public void test2(){
//        System.out.println("3333333");
//        List s2 = appUserMapper.test();
//        System.out.println(s2.get(0).toString());
//    }
}
