package com.refout.trace.redis.config;

/**
 * Number RedisTemplate
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 13:28
 */
@Deprecated
//@Component
public class RedisTemplateNumber extends AbstractRedisTemplate<String, Number> {

    /**
     * 获取值的类型。
     *
     * @return 值的类型
     */
    @Override
    protected Class<Number> clazz() {
        return Number.class;
    }

}
