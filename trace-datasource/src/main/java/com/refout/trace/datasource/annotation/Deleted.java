package com.refout.trace.datasource.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 逻辑删除注解
 * <p>
 * 用于标记逻辑删除的字段或元素
 */
@Documented
@Target({FIELD})
@Retention(RUNTIME)
public @interface Deleted {

}