package com.rehe.modules.admin.system.controller;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* 系统用户(rehe_admin_master.`system_user`)表控制层
*
* @author rehe
*/
@RestController
@RequestMapping("/api/system/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
    * 通过主键查询单条数据
    *
    * @param id 主键
    * @return 单条数据
    */
    @GetMapping("/selectOne")
    public List<User> selectOne(Long id) {
        return userService.selectAll();
    }

}
