package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.dto.reqeust.UserQueryDto;
import com.rehe.modules.admin.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author rehe
 * @description
 * @date 2024/7/4
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    int updatePlatformByUserId(Long id, Integer platformId);

    List<User> selectAll(UserQueryDto userQueryDto);

    User selectByUsername(String username);

    List<User> selectByDeptIds(List<Long> deptIds);

    int insertUserRole(Long userId, Set<Long> roleIds);

    int deleteUserRole(Long userId);

    Set<Long> selectUserRoleIds(Long userId);

    Integer selectUserRoleMaxLevel(Long userId);
}