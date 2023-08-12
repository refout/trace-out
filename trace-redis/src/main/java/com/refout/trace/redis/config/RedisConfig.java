package com.refout.trace.redis.config;

import com.refout.trace.common.util.JsonUtil;
import com.refout.trace.redis.constant.CacheKeyRule;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * Redis配置类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/12 23:42
 */
@EnableCaching
@Configuration
public class RedisConfig {

    /**
     * 缓存前缀
     */
    private static final String PREFIX = "SPRING";

    /**
     * 创建RedisTemplate实例
     *
     * @param connectionFactory Redis连接工厂
     * @param <K>               键类型
     * @param <V>               值类型
     * @return RedisTemplate实例
     */
    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        // 创建Json序列化器
        var jsonRedisSerializer = genericJackson2JsonRedisSerializer();
        // 创建String序列化器
        var stringRedisSerializer = new StringRedisSerializer();

        // 创建RedisTemplate实例
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(connectionFactory);
        // 设置键的序列化器
        redisTemplate.setKeySerializer(stringRedisSerializer);
        // 设置值的序列化器
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        // 设置Hash键的序列化器
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        // 设置Hash值的序列化器
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        // 初始化RedisTemplate
        redisTemplate.afterPropertiesSet();
        // 返回RedisTemplate实例
        return redisTemplate;
    }

    /**
     * 创建RedisCacheManager实例
     *
     * @param connectionFactory Redis连接工厂
     * @return RedisCacheManager实例
     */
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // 创建Redis缓存配置
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.string().getKeySerializationPair())
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                                genericJackson2JsonRedisSerializer()
                        )
                )
                .computePrefixWith((name) -> CacheKeyRule.keyEndWithSeparator(PREFIX, name))
                .entryTtl(Duration.ofHours(1));

        // 构建RedisCacheManager实例
        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(configuration)
                .enableStatistics()
                .build();
    }

    /**
     * 创建GenericJackson2JsonRedisSerializer实例
     *
     * @return GenericJackson2JsonRedisSerializer实例
     */
    @Contract(" -> new")
    private @NotNull GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        // 使用JsonUtil创建GenericJackson2JsonRedisSerializer实例
        return new GenericJackson2JsonRedisSerializer(JsonUtil.mapperWithType());
    }

}