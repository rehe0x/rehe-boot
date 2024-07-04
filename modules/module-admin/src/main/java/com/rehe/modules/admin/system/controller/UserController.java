package com.rehe.modules.admin.system.controller;
import com.github.pagehelper.PageInfo;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.mapstruct.UserMapstruct;
import com.rehe.modules.admin.system.service.UserService;
import com.rehe.modules.admin.system.vo.UserVo;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* 系统用户(rehe_admin_master.`system_user`)表控制层
*
* @author rehe
*/
@RequestMapping("/api/system/user")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
//
//    /**
//    * 通过主键查询单条数据
//    *
//    * @param id 主键
//    * @return 单条数据
//    */
//    @GetMapping("/list")
//    public ResultPage<UserVo> list(Long id) {
//        List<User> list = userService.selectAll();
//
//        return ResultPage.ok();
//    }

}
