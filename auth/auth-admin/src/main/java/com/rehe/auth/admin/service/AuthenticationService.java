package com.rehe.auth.admin.service;

import com.alibaba.fastjson2.JSON;
import com.rehe.auth.admin.dto.AuthMenuDto;
import com.rehe.auth.admin.dto.AuthUserDto;
import com.rehe.auth.admin.dto.JwtUserDto;
import com.rehe.auth.admin.dto.request.AdminLoginDto;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import com.rehe.auth.admin.provider.mobile.MobileAuthenticationToken;
import com.rehe.auth.admin.provider.openid.OpenIdAuthenticationToken;
import com.rehe.auth.admin.dto.response.AuthUserResponseDto;
import com.rehe.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
/**
 * @description
 * @author rehe
 * @date 2024/7/29
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtService jwtService;
    private final AuthUserService authUserService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    public String authPasswd(AdminLoginDto adminLoginDto) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                adminLoginDto.getUsername(),
                adminLoginDto.getPassword()
        );
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        JwtUserDto authUser = (JwtUserDto) authentication.getPrincipal();
        // 用户基本信息存入token
        Map<String,Object> extraClaims = JSON.parseObject(JSON.toJSONString(authUser));
        return jwtService.generateToken(extraClaims, authUser);
    }

    public String authMobile(String phone,String code) {
        authenticationManager.authenticate(
                new MobileAuthenticationToken(
                        phone,code
                )
        );
//        var user = repository.findByEmail(request.getEmail())
//                .orElseThrow();
        String jwtToken = jwtService.generateToken(new JwtUserDto());
//        var refreshToken = jwtService.generateRefreshToken(user);
//        revokeAllUserTokens(user);
//        saveUserToken(user, jwtToken);
        return jwtToken;
    }

    /**
     *
     * @param openId openid和code二选一
     * @param code
     * @return
     */
    public String authOpenId(String code,String openId) {
        authenticationManager.authenticate(
                new OpenIdAuthenticationToken(openId)
        );
        AuthUserDto authUserDto = authUserService.findByOpenId(openId).orElseThrow();
        JwtUserDto authUser = AuthUserMapstruct.INSTANCE.toDto(authUserDto);
        authUser.setPassword(null);
        // 用户基本信息存入token
        Map<String,Object> extraClaims = JSON.parseObject(JSON.toJSONString(authUser));
        return jwtService.generateToken(extraClaims, authUser);
    }


    /**
     * 获取用户信息
     */
    public AuthUserResponseDto userInfo(Long userId) {
        AuthUserDto authUserDto = authUserService.findByUserId(userId).orElseThrow(() -> new BusinessException("当前用户异常"));
        AuthUserResponseDto authUserResponseDto = AuthUserMapstruct.INSTANCE.toResponseDto(authUserDto);

        Integer roleLevel = authUserService.getUserRoleMaxLevel(userId);
        authUserResponseDto.setRoleLevel(roleLevel);

        List<AuthMenuDto> menuDtoList =  authUserService.getUserMenus(authUserResponseDto.getPlatformId(), userId,roleLevel);
        authUserResponseDto.setMenuList(AuthUserMapstruct.INSTANCE.toResponseDto(menuDtoList));
        return authUserResponseDto;
    }

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
