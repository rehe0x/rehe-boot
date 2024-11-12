package com.rehe.common.log;

import java.lang.annotation.*;
/***
 * @description 操作日志注解
 * @author rehe
 * @date 2024/11/11
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OperationLog {
    String value() default ""; // 描述操作内容
}
