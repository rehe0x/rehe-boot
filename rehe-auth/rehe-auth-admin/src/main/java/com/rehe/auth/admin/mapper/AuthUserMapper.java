package com.rehe.auth.admin.mapper;

import com.rehe.auth.admin.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Mapper
public interface AuthUserMapper {
    @Select("SELECT * FROM admin_user where username=#{username} and deleted = 0")
    public User findByUsername(String username);

    @Select("SELECT * FROM admin_user where phone=#{phone} and deleted = 0")
    public User findByPhone(String phone);

    @Select("SELECT * FROM admin_user where open_id=#{openId} and deleted = 0")
    public User findByOpenId(String openId);
}
