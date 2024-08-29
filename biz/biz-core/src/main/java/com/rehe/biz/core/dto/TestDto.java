package com.rehe.biz.core.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * @description
 * @author rehe
 * @date 2024/6/26
 */
@Data
public class TestDto implements Serializable {
    @Serial
    private static final long serialVersionUID = -3637662787191302144L;

    /**
    * ID
    */
    private Long id;

    /**
    * 部门id
    */
    private Long deptId;

    /**
    * 用户名
    */
    private String username;

    /**
    * 密码
    */
    private String password;

    /**
    * 昵称
    */
    private String nickname;

    /**
    * 性别
    */
    private String gender;

    /**
    * 手机号码
    */
    private String phone;

    /**
    * 邮箱
    */
    private String email;

    /**
    * 头像地址
    */
    private String avatarPath;

    /**
    * 1启用、0禁用
    */
    private Integer enabled;

    /**
    * 创建日期
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    /**
    * 软删除
    */
    private Boolean deleted;

    /**
     * 微信id
     */
    private String openId;

    private Integer platformId;

    private Set<Long> roleIds;

}