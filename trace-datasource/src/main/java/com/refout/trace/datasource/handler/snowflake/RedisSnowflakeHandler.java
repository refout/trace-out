package com.refout.trace.datasource.handler.snowflake;


import com.refout.trace.redis.constant.CacheKeyRule;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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
    private static final long MIN_ID = 0;

    /**
     * 最大id
     */
    private static final long MAX_ID = 32;

    /**
     * id数量
     */
    private static final long ID_COUNT = MAX_ID - MIN_ID + 1;

    private Node thisNode = null;

    @Value("${trace.snowflake.node-id.timeout-hour}")
    private int timeoutHour;

    /**
     * Redis响应式
     */
    @Resource
    private RedisTemplate<String, List<Long>> redisTemplate;

    protected Node getNode() {
        Node node = findNode();
        thisNode = node;
        return node;
    }

    protected Node findNode() {
        Set<String> keys = redisTemplate.keys(CacheKey.commonKey());
        if (keys == null || keys.isEmpty()) {
            setNodeToRedis(MIN_ID, MIN_ID);
            return new Node(MIN_ID, MIN_ID);
        }

        Map<Long, List<Long>> map = new TreeMap<>();

        for (String key : keys) {
            if (key == null || key.isBlank()) {
                continue;
            }

            String[] split = key.split(CacheKey.SPLIT_CHAR);
            if (split.length < CacheKey.ITEM_COUNT) {
                continue;
            }

            long datacenterId = Long.parseLong(split[CacheKey.DATACENTER_ID_INDEX]);
            long workerId = Long.parseLong(split[CacheKey.WORKER_ID_INDEX]);
            List<Long> list = map.get(datacenterId);
            if (list == null) {
                list = new ArrayList<>();
            }
            list.add(workerId);
            map.put(datacenterId, list);
        }

        for (var entry : map.entrySet()) {
            List<Long> value = entry.getValue();
            if (value.size() >= ID_COUNT) {
                continue;
            }

            Node node = getId(value::contains, id -> entry.getKey(), id -> id);
            if (node != null) {
                return node;
            }
        }

        if (map.size() >= ID_COUNT) {
            return null;
        }

        return getId(map::containsKey, id -> id, id -> MIN_ID);
    }

    @Scheduled(cron = "${trace.snowflake.node-id.redis-expire-job}")
    public void expiredJob() {
        if (thisNode == null) {
            return;
        }
        redisTemplate.expire(CacheKey.key(thisNode.datacenterId, thisNode.workerId), timeoutHour, TimeUnit.HOURS);
    }

    private @Nullable Node getId(Function<Long, Boolean> notId,
                                 Function<Long, Long> datacenterIdFunc,
                                 Function<Long, Long> workerIdFunc) {
        for (long i = MIN_ID; i <= MAX_ID; i++) {
            if (!notId.apply(i)) {
                Long datacenterId = datacenterIdFunc.apply(i);
                Long workerId = workerIdFunc.apply(i);
                String key = CacheKey.key(datacenterId, workerId);
                Boolean hasKey = redisTemplate.hasKey(key);
                if (hasKey == null || !hasKey) {
                    setNodeToRedis(datacenterId, workerId);
                    return new Node(datacenterId, workerId);
                }
            }
        }
        return null;
    }

    private void setNodeToRedis(long datacenterId, long workerId) {
        redisTemplate.opsForValue().set(
                CacheKey.key(datacenterId, workerId), List.of(datacenterId, workerId),
                timeoutHour, TimeUnit.HOURS);
    }

    protected record Node(long datacenterId, long workerId) {

    }

    private static class CacheKey extends CacheKeyRule {

        private final static String PREFIX = "SNOWFLAKE";

        private final static String SUB_ID = "NODE";

        private final static int DATACENTER_ID_INDEX = 2;

        private final static int WORKER_ID_INDEX = 3;

        private final static int ITEM_COUNT = 4;

        public static String key(long datacenterId, long workerId) {
            return key(PREFIX, String::valueOf, SUB_ID, datacenterId, workerId);
        }

        public static String commonKey() {
            return key(PREFIX, SUB_ID, "*");
        }

    }

}
