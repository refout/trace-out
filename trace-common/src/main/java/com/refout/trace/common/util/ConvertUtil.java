package com.refout.trace.common.util;

import org.springframework.core.convert.ConversionService;

public class ConvertUtil {

    /**
     * 转换服务
     */
    private static final ConversionService conversionService = SpringUtil.getBean(ConversionService.class);

    public static <T> T convert(Object value, T def, Class<T> clazz) {
        if (value == null) {
            return def;
        }

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
            T convert = conversionService.convert(value, clazz);
            return convert == null ? def : convert;
        } catch (Exception e) {
            return def;
        }
    }

}
