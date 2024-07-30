package com.rehe.modules.admin.system.controller;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import com.rehe.common.result.Page;
import com.rehe.common.result.Result;
import com.rehe.common.result.ResultPage;
import com.rehe.common.util.SecurityUtils;
import com.rehe.modules.admin.common.dto.PageParamDto;
import com.rehe.modules.admin.system.dto.UserDto;
import com.rehe.modules.admin.system.dto.reqeust.UserCreateDto;
import com.rehe.modules.admin.system.dto.reqeust.UserQueryDto;
import com.rehe.modules.admin.system.dto.reqeust.UserUpdateDto;
import com.rehe.modules.admin.system.dto.reqeust.UserUpdatePlatformDto;
import com.rehe.modules.admin.system.dto.response.UserResponseDto;
import com.rehe.modules.admin.system.mapstruct.UserMapstruct;
import com.rehe.modules.admin.system.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @description
 * @author rehe
 * @date 2024/7/8
 */
@Tag(name = "用户管理")
@ApiSupport(order = 10)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/system/user")
public class UserController {
    private final UserService userService;

    @Operation(summary = "添加用户",operationId = "1")
    @PreAuthorize("hasAuthority('user:create')")
    @PostMapping("/create")
    public Result<Void> create(@RequestBody @Valid UserCreateDto userCreateDto) {
        userService.createUser(userCreateDto);
        return Result.ok();
    }

    @Operation(summary = "修改用户", operationId = "3")
    @PreAuthorize("hasAuthority('user:update')")
    @PostMapping("/update")
    public Result<Void> update(@RequestBody @Valid UserUpdateDto userUpdateDto) {
        userService.updateUser(userUpdateDto);
        return Result.ok();
    }

    @Operation(summary = "更新系统ID", operationId = "20")
    @PostMapping("/update/platform/{platformId}")
    public Result<Void> updateUserPlatform(@Parameter(description = "系统ID") @PathVariable Integer platformId) {
        userService.updateUserPlatform(SecurityUtils.getLoginUserId(), platformId);
        return Result.ok();
    }

    @Operation(summary = "删除用户", operationId = "4")
    @PreAuthorize("hasAuthority('user:delete')")
    @PostMapping("/delete/{id}")
    public Result<Void> delete(@Parameter(description = "用户ID") @PathVariable Long id) {
        userService.deleteUser(id);
        return Result.ok();
    }

    @Operation(summary = "用户列表", operationId = "10")
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/query")
    public ResultPage<UserResponseDto> query(@ParameterObject @Valid UserQueryDto userQueryDto,
                                             @ParameterObject PageParamDto pageParamDto) {
        Page<UserDto> pageInfo = userService.queryUser(userQueryDto, pageParamDto);
        return ResultPage.ok(UserMapstruct.INSTANCE.toUserResponseDto(pageInfo));
    }

    @Operation(summary = "用户详情", operationId = "12")
    @PreAuthorize("hasAuthority('user')")
    @GetMapping("/get/{id}")
    public Result<UserResponseDto> getById(@Parameter(description = "用户ID") @PathVariable Long id) {
        UserResponseDto userVo = UserMapstruct.INSTANCE.toUserResponseDto(userService.getUserById(id));
        return Result.ok(userVo);
    }

}
