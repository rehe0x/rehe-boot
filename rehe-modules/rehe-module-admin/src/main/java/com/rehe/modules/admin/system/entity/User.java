package com.rehe.modules.admin.system.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 系统用户
 */
@Data
public class User implements Serializable {
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
    private Boolean enabled;

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

    private static final long serialVersionUID = 1L;
}