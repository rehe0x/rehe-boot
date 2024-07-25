package com.rehe.modules.admin.system.controller;
import com.rehe.modules.admin.system.entity.Role;
import com.rehe.modules.admin.system.service.RoleService;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

/**
* 角色表(system_role)表控制层
*
* @author xxxxx
*/
@RestController
@RequestMapping("/system_role")
public class RoleController {
/**
* 服务对象
*/
    @Autowired
    private RoleService roleService;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("selectOne")
    public Role selectOne(Integer id) {
    return roleService.selectByPrimaryKey(id);
    }

}
