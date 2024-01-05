package com.rehe.modules.admin.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiech
 * @since 2024-01-05
 */
@TableName("admin_user")
public class AdminUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;

    private String ssfDle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSsfDle() {
        return ssfDle;
    }

    public void setSsfDle(String ssfDle) {
        this.ssfDle = ssfDle;
    }

    @Override
    public String toString() {
        return "AdminUser{" +
            "id = " + id +
            ", username = " + username +
            ", password = " + password +
            ", ssfDle = " + ssfDle +
        "}";
    }
}
