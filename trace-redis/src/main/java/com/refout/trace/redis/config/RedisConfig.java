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

@EnableCaching
@Configuration
public class RedisConfig {

    private static final String PREFIX = "SPRING";

    @Bean
    public <K, V> RedisTemplate<K, V> redisTemplate(RedisConnectionFactory connectionFactory) {
        var jsonRedisSerializer = genericJackson2JsonRedisSerializer();
        var stringRedisSerializer = new StringRedisSerializer();

        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setValueSerializer(jsonRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);
        redisTemplate.setHashValueSerializer(jsonRedisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig()
                .serializeKeysWith(RedisSerializationContext.string().getKeySerializationPair())
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(
                                genericJackson2JsonRedisSerializer()
                        )
                )
                .computePrefixWith((name) -> CacheKeyRule.keyEndWithSeparator(PREFIX, name))
                .entryTtl(Duration.ofHours(1));

        return RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(configuration)
                .enableStatistics()
                .build();
    }

    @Contract(" -> new")
    private @NotNull GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer() {
        return new GenericJackson2JsonRedisSerializer(JsonUtil.mapperWithType());
    }

}
