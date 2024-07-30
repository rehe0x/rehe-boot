package com.rehe.auth.admin.mapper;

import com.rehe.auth.admin.dto.AuthMenuDto;
import com.rehe.auth.admin.entity.User;
import com.rehe.auth.admin.dto.response.AuthMenuResponseDto;
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

    User selectByUserId(Long userId);

    User selectByUsername(String username);

    User selectByPhone(String phone);

    User selectByOpenId(String openId);

    List<AuthMenuDto> selectMenuByUser(Integer platformId, Long userId);

    List<Integer> selectPlatformByUser(Long userId);
}
