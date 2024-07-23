package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.dto.UserQueryDto;
import com.rehe.modules.admin.system.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    List<User> selectAll(UserQueryDto userQueryDto);

    User selectByUsername(String username);

    List<User> selectByDeptIds(List<Long> deptIds);
}