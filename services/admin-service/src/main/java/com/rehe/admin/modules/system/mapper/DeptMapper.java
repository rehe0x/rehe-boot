package com.rehe.admin.modules.system.mapper;

import com.rehe.admin.modules.system.entity.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Mapper
public interface DeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);

    int deleteByPrimaryKeys(List<Long> ids);

    List<Dept> selectAll();
}