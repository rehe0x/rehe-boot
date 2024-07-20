package com.rehe.auth.admin.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * @author xiech
 * @description
 * @date 2024/1/11
 */

@Data
@Builder
@Schema(description = "用户信息Vo")
public class AuthUserInfoVo {
    @Schema(description = "user id")
    private Long id;
    @Schema(description = "头像")
    private String avatar;
    @Schema(description = "昵称")
    private String nickname;
    @Schema(description = "用户名")
    private String username;
    @Schema(description = "手机")
    private String phone;
    @Schema(description = "邮箱")
    private String email;

    private List<AuthMenuVo> menuList;
}
