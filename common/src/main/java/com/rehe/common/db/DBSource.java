package com.rehe.common.db;

import java.lang.annotation.*;

/**
 * 数据源注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface DBSource {
    DynamicDataSourceEnum value() default DynamicDataSourceEnum.MASTER;
    boolean clear() default true;
}
