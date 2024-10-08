package com.rehe.admin.modules.system.dto.response;

import java.time.LocalDateTime;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author rehe
 */
@Schema(description="系统用户")
@Data
public class UserResponseDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    private Long id;

    /**
     * 部门id
     */
    @Schema(description = "部门id")
    private Long deptId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    private String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别")
    private String gender;

    /**
     * 手机号码
     */
    @Schema(description = "手机号码")
    private String phone;

    /**
     * 邮箱
     */
    @Schema(description = "邮箱")
    private String email;

    /**
     * 头像地址
     */
    @Schema(description = "头像地址")
    private String avatarPath;

    /**
     * 1启用、0禁用
     */
    @Schema(description = "1启用、0禁用")
    private Integer enabled;

    /**
     * 创建日期
     */
    @Schema(description = "创建日期")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 软删除
     */
    @Schema(description = "软删除")
    private Boolean deleted;

    @Schema(description = "关联角色ID")
    private Set<Long> roleIds;
}