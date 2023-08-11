package com.refout.trace.redis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.common.util.SpringUtil;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * 抽象RedisTemplate类用于操作Redis缓存。
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 * @author oo w
 * @version 1.0
 * @since 2023/7/31 12:46
 */
@Deprecated
public abstract class AbstractRedisTemplate<K, V> extends RedisTemplate<K, V> {

    /**
     * 构造函数，初始化RedisTemplate实例。
     */
    public AbstractRedisTemplate() {
        // 创建JsonUtil的ObjectMapper的副本
        ObjectMapper mapper = JsonUtil.mapper.copy();
        // 创建Jackson2JsonRedisSerializer，使用mapper和类类型
        var jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(mapper, clazz());
        // 创建StringRedisSerializer
        var stringRedisSerializer = new StringRedisSerializer();
        // 从SpringUtil获取RedisConnectionFactory bean
        RedisConnectionFactory connectionFactory = SpringUtil.getBean(RedisConnectionFactory.class);
        // 设置连接工厂、序列化器，并在属性设置完成后调用afterPropertiesSet方法
        setConnectionFactory(connectionFactory);
        setKeySerializer(stringRedisSerializer);
        setValueSerializer(jsonRedisSerializer);
        setHashKeySerializer(stringRedisSerializer);
        setHashValueSerializer(jsonRedisSerializer);
        afterPropertiesSet();
    }

    /**
     * 获取值的类型。
     *
     * @return 值的类型
     */
    protected abstract Class<V> clazz();

}