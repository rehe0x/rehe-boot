package com.rehe.modules.admin.system.controller;
import com.github.pagehelper.PageInfo;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.UserAddDto;
import com.rehe.modules.admin.system.dto.UserQueryDto;
import com.rehe.modules.admin.system.dto.UserUpdateDto;
import com.rehe.modules.admin.system.entity.User;
import com.rehe.modules.admin.system.mapstruct.UserMapstruct;
import com.rehe.modules.admin.system.service.UserService;
import com.rehe.modules.admin.system.vo.UserVo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
* 系统用户(rehe_admin_master.`system_user`)表控制层
*
* @author rehe
*/
@Tag(name = "用户管理")
@ApiSupport(order = 10)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "添加用户",operationId = "1")
    @PostMapping("/add")
    public Result<Void> add(@RequestBody @Valid UserAddDto userAddDto) {
        userService.addUser(userAddDto);
        return Result.ok();
    }

    @Operation(summary = "修改用户", operationId = "3")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "删除用户", operationId = "4")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok();
    }

    @Operation(summary = "用户列表", operationId = "10")
    @GetMapping("/query")
    public ResultPage<UserVo> query(@ParameterObject @Valid UserQueryDto userQueryDto,
                                    @ParameterObject PageParamDto pageParamDto) {
        Page<UserVo> pageInfo = userService.queryUsers(userQueryDto, pageParamDto);
        return ResultPage.ok(pageInfo);
    }

    @Operation(summary = "用户详情", operationId = "12")
    @GetMapping("/get/{id}")
    public Result<UserVo> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        UserVo userVo = userService.getUserById(id);
        return Result.ok(userVo);
    }
}
