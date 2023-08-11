package com.refout.trace.redis.constant;

import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

/**
 * 用于生成和管理缓存键。
 * <p>
 * 该类包含一个私有静态常量SPLIT_CHAR，表示缓存键中的分隔符为冒号。
 * <p>
 * 私有方法"key(String... subKeys)"用于生成缓存键。它接受可变参数subKeys，用于指定缓存键的子键。
 * 方法会将前缀和子键连接起来，并使用冒号作为分隔符生成最终的缓存键。
 * 如果subKeys为空或长度为0，则返回null。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/6 17:09
 */
public abstract class CacheKeyRule {

    /**
     * 缓存键分隔符
     */
    public static final String SEPARATOR = ":";

    /**
     * 生成缓存键的方法。
     *
     * @param subKeys 子键数组，用于指定缓存键的子键。
     * @return 生成的缓存键，如果子键为空或长度为0，则返回null。
     */
    public static @NotNull String key(String prefix, String... subKeys) {
        return key(prefix,String::valueOf, (Object[]) subKeys);
    }

    /**
     * 生成缓存键的方法，末尾保留分隔符。
     *
     * @param subKeys 子键数组，用于指定缓存键的子键。
     * @return 生成的缓存键，如果子键为空或长度为0，则返回null。
     */
    public static String keyEndWithSeparator(String prefix, String... subKeys) {
        return keyEndWithSeparator(prefix, String::valueOf, (Object[]) subKeys);
    }

    /**
     * 生成缓存键的方法。
     *
     * @param subKeys 子键数组，用于指定缓存键的子键。
     * @return 生成的缓存键，如果子键为空或长度为0，则返回null。
     */
    public static @NotNull String key(String prefix, Number... subKeys) {
        return key(prefix, Object::toString, (Object[]) subKeys);
    }

    /**
     * 生成缓存键的方法。
     *
     * @param subKeys 子键数组，用于指定缓存键的子键。
     * @return 生成的缓存键，如果子键为空或长度为0，则返回null。
     */
    public static @NotNull String key(String prefix, Function<Object, String> toString, Object... subKeys) {
        String withSeparator = keyEndWithSeparator(prefix, toString, subKeys);
        return withSeparator.substring(0, withSeparator.lastIndexOf(SEPARATOR));
    }

    /**
     * 生成缓存键的方法，末尾保留分隔符。
     *
     * @param subKeys 子键数组，用于指定缓存键的子键。
     * @return 生成的缓存键，如果子键为空或长度为0，则返回null。
     */
    public static String keyEndWithSeparator(String prefix, Function<Object, String> toString, Object... subKeys) {
        if (prefix == null) {
            return null;
        }
        if (subKeys == null || subKeys.length == 0) {
            return null;
        }
        StringBuilder key = new StringBuilder();
        key.append(prefix.toUpperCase()).append(SEPARATOR);
        for (Object subKey : subKeys) {
            String str = toString.apply(subKey);
            if (str == null || str.isBlank()) {
                return null;
            }
            key.append(str.toUpperCase()).append(SEPARATOR);
        }

        return key.toString();
    }

}
