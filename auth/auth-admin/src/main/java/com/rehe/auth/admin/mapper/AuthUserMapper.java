package com.rehe.auth.admin.mapper;

import com.rehe.auth.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Mapper
public interface AuthUserMapper {

    User findByUsername(String username);

    User findByPhone(String phone);

    User findByOpenId(String openId);
}
