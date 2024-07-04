package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author rehe
 * @description
 * @date 2024/6/26
 */
@Mapper
 public interface MenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> selectAll();

    Menu selectByPidRoute(@Param("parentId") Long parentId, @Param("routePath") String routePath);

    Menu selectByPermission(@Param("permission") String permission);
}