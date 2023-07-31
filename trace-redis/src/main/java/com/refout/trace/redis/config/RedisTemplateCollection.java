package com.refout.trace.redis.config;

import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Collection RedisTemplate
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 13:27
 */
@Component
@SuppressWarnings("rawtypes")
public class RedisTemplateCollection extends AbstractRedisTemplate<String, Collection> {

    /**
     * 获取值的类型。
     *
     * @return 值的类型
     */
    @Override
    protected Class<Collection> clazz() {
        return Collection.class;
    }

}
