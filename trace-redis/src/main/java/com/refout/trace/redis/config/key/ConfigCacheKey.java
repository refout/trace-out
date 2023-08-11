package com.refout.trace.redis.config.key;

import com.refout.trace.redis.constant.CacheKeyRule;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 配置缓存键生成器
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/11 17:30
 */
@Component("ConfigCacheKey")
public class ConfigCacheKey extends CacheKeyRule implements KeyGenerator {

    /**
     * 缓存键的前缀
     */
    public static final String PREFIX = "CONFIG";

    /**
     * 生成给定方法及其参数的缓存键。
     *
     * @param target 目标实例
     * @param method 调用的方法
     * @param params 方法参数（包括任何可变参数的展开）
     * @return 生成的缓存键
     */
    @Override
    public @NotNull Object generate(
            @NotNull Object target, @NotNull Method method, Object @NotNull ... params) {
        if (params.length == 0) {
            return method.getName();
        }
        return key((String) params[1], (String) params[0]);
    }

}