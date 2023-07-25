package com.refout.trace.datasource.handler;


import com.refout.trace.common.util.RandomUtil;
import com.refout.trace.redis.constant.CacheKeyRule;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/16 22:26
 */
@Component
public class RedisSnowflakeHandler {

    /**
     * 最小id
     */
    private static final int MIN_ID = 0;

    /**
     * 最大id
     */
    private static final int MAX_ID = 32;

    /**
     * id数量
     */
    private static final int ID_COUNT = MAX_ID - MIN_ID + 1;

    private static final int ID_AMOUNT = ID_COUNT * ID_COUNT;

    /**
     * Redis响应式
     */
    @Resource

    private RedisTemplate<String, List<Integer>> redisTemplate;

    public List<Integer> getDatacenterAndWorkerId() {
        int count = 0;
        while (count++ < ID_AMOUNT) {
            CacheKey.Key randomKey = CacheKey.randomKey();
            String key = randomKey.key;
            Boolean hasKey = redisTemplate.hasKey(key);
            if (hasKey == null || !hasKey) {
                List<Integer> list = List.of(randomKey.datacenterId, randomKey.workerId);
                redisTemplate.opsForValue().set(key, list);
                return list;
            }
        }
        return null;
    }

    private static class CacheKey extends CacheKeyRule {

        private final static String PREFIX = "SNOWFLAKE";

        private final static String SUB_ID = "DATACENTER_WORKER_ID";

        public static Key randomKey() {
            int datacenterId = getRandomInt();
            int workerId = getRandomInt();
            String key = key(PREFIX, String::valueOf, SUB_ID, datacenterId, workerId);
            return new Key(key, datacenterId, workerId);
        }

        public record Key(String key, int datacenterId, int workerId) {

        }

        private static int getRandomInt() {
            return RandomUtil.randomInt(MIN_ID, MAX_ID);
        }

    }

}
