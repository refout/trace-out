package com.refout.trace.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;

/**
 * 反射工具类，继承自 ReflectionUtils
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 20:48
 */
public class ReflectionUtil extends ReflectionUtils {

    /**
     * 获取指定类中满足条件的字段数组
     *
     * @param clazz  要获取字段的类
     * @param filter 字段过滤条件
     * @return 满足条件的字段数组
     */
    public static Field @NotNull [] getFields(@NotNull Class<?> clazz, Predicate<Field> filter) {
        // 获取指定类中声明的所有字段
        Field[] fields = clazz.getDeclaredFields();
        // 使用流过滤满足条件的字段，并将结果转换为数组返回
        return Arrays.stream(fields).filter(filter).toArray(Field[]::new);
    }

    /**
     * 获取指定类中指定类型的字段数组
     *
     * @param clazz      要获取字段的类
     * @param fieldClazz 字段的类型
     * @return 指定类型的字段数组
     */
    public static Field @NotNull [] getFields(Class<?> clazz, Class<?> fieldClazz) {
        // 调用 getFields 方法，传入字段过滤条件为字段类型与指定类型相等
        return getFields(clazz, f -> f.getType().equals(fieldClazz));
    }

}