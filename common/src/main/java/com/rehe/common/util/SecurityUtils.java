package com.rehe.common.util;

import com.alibaba.fastjson2.JSONObject;
import com.rehe.common.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author xiech
 * @description
 * @date 2024/7/29
 */
public class SecurityUtils {

    public static Long getLoginUserId() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException("当前登录状态过期");
        }
        if (authentication.getPrincipal() instanceof UserDetails) {
            Long userId = JSONObject.parseObject(JSONObject.toJSONString(authentication.getPrincipal())).getLong("userId");
            if (userId == null) {
                throw new BusinessException("当前登录状态过期");
            }
            return userId;
        }
        throw new BusinessException("当前登录状态过期");
    }
}
