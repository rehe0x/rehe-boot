package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.entity.Dict;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rehe
 * @description
 * @date 2024/8/2
 */
@Mapper
public interface DictMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Dict record);

    int insertSelective(Dict record);

    Dict selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Dict record);

    int updateByPrimaryKey(Dict record);

    List<Dict> selectAll();

    Dict selectByCode(String code);

}