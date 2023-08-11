package com.refout.trace.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.function.Predicate;

public class ReflectionUtil extends ReflectionUtils {

    public static Field @NotNull [] getFields(@NotNull Class<?> clazz, Predicate<Field> filter) {
        Field[] fields = clazz.getDeclaredFields();
        return Arrays.stream(fields).filter(filter).toArray(Field[]::new);
    }

    public static Field @NotNull [] getFields(Class<?> clazz, Class<?> fieldClazz) {
        return getFields(clazz, f -> f.getType().equals(fieldClazz));
    }

}
