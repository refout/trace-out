package com.refout.trace.redis.handler;

import com.refout.trace.common.util.SpringUtil;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * redis 重试次数管理
 *
 * @author oo w
 * @version 1.0
 * @since 2023/9/21 17:49
 */
public class RedisRetry {

    /**
     * 用于操作 Redis 数据库
     */
    @SuppressWarnings("unchecked")
    private static final RedisTemplate<String, Integer> redisTemplate = SpringUtil.getBean(RedisTemplate.class);

    /**
     * 获取指定键的重试次数
     *
     * @param redisKey redis key
     * @return 重试次数
     */
    public static int getRetry(final String redisKey) {
        // 从Redis中获取计数值
        Integer count = redisTemplate.opsForValue().get(redisKey);
        // 如果计数值为空，则将其设置为0
        if (count == null) {
            count = 0;
        }
        // 返回计数值
        return count;
    }

    /**
     * 记录指定键的重试次数
     *
     * @param redisKey redis key
     * @param timeout  过期超时时间
     * @param unit     时间单位
     */
    public static void recordRetry(final String redisKey, long timeout, TimeUnit unit) {
        // 将重试次数加1，并设置到Redis中，设置过期时间
        redisTemplate.opsForValue().increment(redisKey);
        redisTemplate.expire(redisKey, timeout, unit);
    }

    /**
     * 移除指定键的重试次数
     *
     * @param redisKey redis key
     */
    public static void removeRetry(final String redisKey) {
        // 从Redis中删除计数键
        redisTemplate.delete(redisKey);
    }

}
