package com.rehe.biz.core.mapper;

import com.rehe.biz.core.entity.TestBiz;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

/**
 * @author rehe
 * @description
 * @date 2024/7/4
 */
@Mapper
public interface TestBizMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TestBiz record);

}