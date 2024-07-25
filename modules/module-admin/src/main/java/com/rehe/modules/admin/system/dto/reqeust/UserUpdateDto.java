package com.rehe.modules.admin.system.dto.reqeust;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author rehe
 */
@Schema(description="修改用户")
@Data
public class UserUpdateDto {
    /**
     * ID
     */
    @Schema(description = "ID")
    @NotNull(message = "ID不能为空")
    private Long id;

    /**
     * 部门id
     */
    @Schema(description = "部门id 默认=0")
    private Long deptId;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    @NotBlank(message = "用户名不能为空")
    @Size(max = 20, min = 2,message = "用户名2-20字符")
    private String username;

    /**
     * 密码
     */
    @Schema(description = "密码 默认123123")
    private String password = "123123";

    /**
     * 昵称
     */
    @Schema(description = "昵称")
    @NotBlank(message = "昵称不能为空")
    @Size(max = 20, min = 2,message = "昵称2-20字符")
    private String nickname;

    /**
     * 性别
     */
    @Schema(description = "性别 男｜女")
    @NotBlank(message = "性别不能为空")
    private String gender;

    @AssertTrue(message = "性别必须为男｜女")
    private boolean isGender() {
        return "男".equals(gender) || "女".equals(gender);
    }

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
    @Schema(description = "1启用、0禁用 默认=1")
    private Integer enabled;
}