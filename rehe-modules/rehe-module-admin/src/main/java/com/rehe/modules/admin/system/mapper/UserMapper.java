package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rehe
 */
@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectAll();
}