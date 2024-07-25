package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.dto.reqeust.MenuQueryDto;
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

    int deleteByPrimaryKeys(List<Long> ids);

    List<Menu> selectAll(MenuQueryDto menuQueryDto);

    Menu selectByPidRoute(@Param("platformId") Integer platformId,@Param("parentId") Long parentId, @Param("routePath") String routePath);

    Menu selectByPermission(@Param("platformId") Integer platformId,@Param("permission") String permission);
}