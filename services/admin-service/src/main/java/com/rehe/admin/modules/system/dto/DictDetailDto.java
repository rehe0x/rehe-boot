package com.rehe.admin.modules.system.dto;

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
public class DictDetailDto implements Serializable {
    /**
    * ID
    */
    private Long id;

    /**
    * 字典id
    */
    private Long dictId;

    /**
    * 字典标签
    */
    private String label;

    /**
    * 字典值
    */
    private String value;

    /**
    * 排序
    */
    private Integer sort;

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