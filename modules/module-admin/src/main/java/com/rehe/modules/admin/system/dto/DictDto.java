package com.rehe.modules.admin.system.dto;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @description
 * @author rehe
 * @date 2024/8/2
 */
@Data
public class DictDto implements Serializable {
    /**
    * ID
    */
    private Long id;

    /**
    * 字典代码
    */
    private String code;

    /**
    * 字典名称
    */
    private String name;

    /**
    * 创建日期
    */
    private LocalDateTime createTime;

    /**
    * 更新时间
    */
    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}