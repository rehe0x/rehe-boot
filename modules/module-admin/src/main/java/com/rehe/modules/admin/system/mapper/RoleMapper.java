package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.dto.reqeust.RoleMenuBindDto;
import com.rehe.modules.admin.system.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author rehe
 * @description
 * @date 2024/7/23
 */
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> selectList();

    Role selectByName(String name);

    int insertRoleMenu(Long roleId, Set<Long> menuIds);

    int deleteRoleMenuByRoleId(Long roleId);

    int selectRoleUserCount(Long roleId);
}