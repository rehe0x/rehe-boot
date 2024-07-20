package com.rehe.auth.admin.mapper;

import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.vo.AuthMenuVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @author xiech
 * @description
 * @date 2024/1/4
 */
@Mapper
public interface AuthUserMapper {

    User selectByUsername(String username);

    User selectByPhone(String phone);

    User selectByOpenId(String openId);

    List<AuthMenuVo> selectMenuByUser(@Param("platformId") Integer platformId, Long userId);
}
