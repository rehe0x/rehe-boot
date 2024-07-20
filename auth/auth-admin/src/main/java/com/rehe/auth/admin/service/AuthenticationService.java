package com.rehe.auth.admin.service;

import com.alibaba.fastjson2.JSON;
import com.rehe.auth.admin.dto.AdminLoginDto;
import com.rehe.auth.admin.mapper.AuthUserMapper;
import com.rehe.auth.admin.mapstruct.AuthUserMapstruct;
import com.rehe.auth.admin.provider.mobile.MobileAuthenticationToken;
import com.rehe.auth.admin.provider.openid.OpenIdAuthenticationToken;
import com.rehe.auth.admin.entity.AuthUser;
import com.rehe.auth.admin.vo.AuthMenuVo;
import com.rehe.auth.admin.vo.AuthUserInfoVo;
import com.rehe.common.exception.BusinessException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        AuthUser authUser = authUserService.findByUsername(adminLoginDto.getUsername()).orElseThrow();
        authUser.setPassword(null);
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
        String jwtToken = jwtService.generateToken(new AuthUser());
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
        AuthUser authUser = authUserService.findByOpenId(openId);
        authUser.setPassword(null);
        // 用户基本信息存入token
        Map<String,Object> extraClaims = JSON.parseObject(JSON.toJSONString(authUser));
        return jwtService.generateToken(extraClaims, authUser);
    }


    /**
     * 获取用户信息
     */
    public AuthUserInfoVo userInfo(int platformId) {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("当前登录状态过期");
        }

        //临时
//        AuthUserInfoVo authUser  = AuthUserMapstruct.INSTANCE.toVo(userDetails);
        AuthUserInfoVo authUser= AuthUserInfoVo.builder().username("潘西").build();
        List<AuthMenuVo> menuVoList =  authUserService.getUserMenus(platformId, null);
        authUser.setMenuList(menuVoList);
        return authUser;
//        if (authentication.getPrincipal() instanceof AuthUser userDetails) {
//            AuthUserInfoVo authUser  = AuthUserMapstruct.INSTANCE.toVo(userDetails);
//            List<AuthMenuVo> menuVoList =  authUserService.getUserMenus(1, null);
//            authUser.setMenuList(menuVoList);
//            return authUser;
//        }
//        return null;
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
