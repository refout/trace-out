package com.refout.trace.redis.config;

/**
 * String RedisTemplate
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 13:28
 */
@Deprecated
//@Component
public class RedisTemplateStr extends AbstractRedisTemplate<String, String> {

    /**
     * 获取值的类型。
     *
     * @return 值的类型
     */
    @Override
    protected Class<String> clazz() {
        return String.class;
    }

}
