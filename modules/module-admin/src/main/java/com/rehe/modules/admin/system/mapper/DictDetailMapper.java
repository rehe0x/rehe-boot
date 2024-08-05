package com.rehe.modules.admin.system.mapper;

import com.rehe.modules.admin.system.entity.DictDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author rehe
 * @description
 * @date 2024/8/2
 */
@Mapper
public interface DictDetailMapper {
    int deleteByPrimaryKey(Long id);

    int insert(DictDetail record);

    int insertSelective(DictDetail record);

    DictDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DictDetail record);

    int updateByPrimaryKey(DictDetail record);

    List<DictDetail> selectAll();

    List<DictDetail> selectByDictId(Long dictId);

    int deleteByDictId(Long dictId);

}