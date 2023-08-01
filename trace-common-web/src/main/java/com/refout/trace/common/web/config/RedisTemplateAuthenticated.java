package com.refout.trace.common.web.config;

import com.refout.trace.common.system.domain.authenticated.Authenticated;
import com.refout.trace.redis.config.AbstractRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Authenticated RedisTemplate
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 13:27
 */
@Component
public class RedisTemplateAuthenticated extends AbstractRedisTemplate<String, Authenticated> {

    /**
     * 获取值的类型。
     *
     * @return 值的类型
     */
    @Override
    protected Class<Authenticated> clazz() {
        return Authenticated.class;
    }

}