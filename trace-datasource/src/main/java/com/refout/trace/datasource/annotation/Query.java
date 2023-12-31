package com.refout.trace.datasource.annotation;

import com.refout.trace.datasource.enums.QueryFunc;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 查询注解，用于指定查询的方式。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:42
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface Query {

    /**
     * 获取查询方式。
     *
     * @return 查询方式
     */
    QueryFunc value() default QueryFunc.LIKE;

}