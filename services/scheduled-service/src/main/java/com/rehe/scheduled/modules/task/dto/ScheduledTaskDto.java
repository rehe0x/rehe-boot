package com.rehe.scheduled.modules.task.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ScheduledTaskDto implements Serializable {
    private Long id;

    /**
    * 任务ID备用
    */
    private String taskId;

    /**
    * 任务名称
    */
    private String taskName;

    /**
    * 实例名称
    */
    private String beanName;

    /**
    * 方法名称
    */
    private String methodName;

    /**
    * 参数
    */
    private String argument;

    /**
    * 子任务
    */
    private String subTask;

    /**
    * 任务方式 1正则表达式 2固定时间间隔 3固定延迟
    */
    private Short taskMode;

    /**
    * 表达式
    */
    private String expression;

    private String description;

    /**
     * 任务状态1正常 0暂停
     */
    private Short status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Boolean deleted;

    private static final long serialVersionUID = 1L;
}