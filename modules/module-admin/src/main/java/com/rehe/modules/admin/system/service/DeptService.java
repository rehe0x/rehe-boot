package com.rehe.modules.admin.system.service;

import com.rehe.modules.admin.system.mapstruct.DeptMapstruct;
import com.rehe.modules.admin.system.vo.DeptVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.rehe.modules.admin.system.mapper.DeptMapper;
import com.rehe.modules.admin.system.entity.Dept;

import java.util.List;

/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Service
@RequiredArgsConstructor
public class DeptService{

    private final DeptMapper deptMapper;

    public List<DeptVo> queryDepts() {
       List<Dept> deptList = deptMapper.selectAll();
       return DeptMapstruct.INSTANCE.toVo(deptList);
    }
}
