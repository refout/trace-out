package com.refout.trace.common.util;

import org.springframework.core.convert.ConversionService;

/**
 * ConvertUtil类是一个通用的类型转换工具类，用于将一个值转换为指定类型的对象。
 * 它使用ConversionService进行类型转换，并提供了一些额外的转换逻辑。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/16 21:45
 */
public class ConvertUtil {

    /**
     * 转换服务
     */
    private static final ConversionService conversionService = SpringUtil.getBean(ConversionService.class);

    /**
     * 将一个值转换为指定类型的对象
     *
     * @param value 值
     * @param def   默认值
     * @param clazz 目标类型
     * @param <T>   目标类型参数
     * @return 转换后的对象
     */
    public static <T> T convert(Object value, T def, Class<T> clazz) {
        // 如果值为空，则返回默认值
        if (value == null) {
            return def;
        }

        // 如果值是字符串类型，并且是JSON格式的字符串，则使用JsonUtil进行转换
        if (value instanceof String string) {
            if (JsonUtil.isJson(string)) {
                T t = JsonUtil.fromJson(string, clazz);
                if (t == null) {
                    return def;
                }
                return t;
            }
        }

        try {
            // 使用ConversionService进行类型转换
            T convert = conversionService.convert(value, clazz);
            return convert == null ? def : convert;
        } catch (Exception e) {
            return def;
        }
    }

}