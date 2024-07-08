package com.rehe.modules.admin.system.controller;
import com.rehe.modules.admin.system.entity.Dept;
import com.rehe.modules.admin.system.service.DeptService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 部门(system_dept)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/system_dept")
public class DeptController {
/**
* 服务对象
*/
    @Autowired
    private DeptService deptService;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public Dept selectOne(Integer id) {
    return deptService.selectByPrimaryKey(id);
    }

}
