package com.refout.trace.datasource.handler.snowflake;

import com.refout.trace.redis.constant.CacheKeyRule;
import jakarta.annotation.PreDestroy;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * redis雪花算法datacenterId和workerId生成处理器
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/16 22:26
 */
@Slf4j
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

    /**
     * 当前机器获取到的datacenterId和workerId
     */
    private Node thisNode = null;

    private Snowflake snowflake;

    /**
     * datacenterId和workerId在redis中过期的时间
     */
    @Value("${trace.snowflake.node-id.timeout-hour}")
    private int timeoutHour;

    /**
     * RedisTemplate
     */
    @Resource
    private RedisTemplate<String, List<Long>> redisTemplate;

    public Snowflake getSnowflake() {
        if (snowflake == null) {
            Node node = getNode();
            if (node == null) {
                throw new RuntimeException("datacenter_id,worker_id has been exhausted!");
            }
            snowflake = new Snowflake(node.datacenterId(), node.workerId());
            log.debug("datacenterId:{},workerId:{}", node.datacenterId(), node.workerId());
        }
        return snowflake;
    }

    /**
     * 获取Node节点，datacenterId和workerId
     *
     * @return datacenterId和workerId
     */
    protected Node getNode() {
        if (thisNode == null) {
            thisNode = findNode();
            log.debug("从redis获取当前节点datacenterId:{},workerId:{}", thisNode.datacenterId, thisNode.workerId);
        }

        return thisNode;
    }

    /**
     * 查找Node节点
     *
     * @return datacenterId和workerId
     */
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

            String[] split = key.split(CacheKey.SEPARATOR);
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

            Node node = getIdFromRedis(value::contains, id -> entry.getKey(), id -> id);
            if (node != null) {
                return node;
            }
        }

        if (map.size() >= ID_COUNT) {
            return null;
        }

        return getIdFromRedis(map::containsKey, id -> id, id -> MIN_ID);
    }

    /**
     * 雪花算法datacenterId和workerId过期时间续期
     */
    @Scheduled(cron = "${trace.snowflake.node-id.redis-expire-job}")
    public void expiredJob() {
        if (thisNode == null) {
            return;
        }
        redisTemplate.expire(CacheKey.key(thisNode.datacenterId, thisNode.workerId), timeoutHour, TimeUnit.HOURS);
    }

    /**
     * 服务停止，从redis中删除当前节点datacenterId,workerId
     */
    @PreDestroy
    public void removeNode() {
        if (thisNode == null) {
            return;
        }
        long workerId = thisNode.workerId;
        long datacenterId = thisNode.datacenterId;
        String key = CacheKey.key(datacenterId, workerId);
        Boolean delete = redisTemplate.delete(key);
        log.debug("服务停止，从redis中删除当前节点datacenterId:{},workerId:{}，删除结果：{}", datacenterId, workerId, delete);
    }

    /**
     * 获取ID节点。
     *
     * @param notId            排除的ID函数
     * @param datacenterIdFunc 数据中心ID函数
     * @param workerIdFunc     工作节点ID函数
     * @return ID节点对象，如果没有可用的节点则返回null
     */
    private @Nullable Node getIdFromRedis(Function<Long, Boolean> notId,
                                          Function<Long, Long> datacenterIdFunc,
                                          Function<Long, Long> workerIdFunc) {
        for (long i = MIN_ID; i <= MAX_ID; i++) {
            if (!notId.apply(i)) {
                // 根据ID获取数据中心ID和工作节点ID
                Long datacenterId = datacenterIdFunc.apply(i);
                Long workerId = workerIdFunc.apply(i);
                // 生成缓存键
                String key = CacheKey.key(datacenterId, workerId);
                // 判断缓存中是否存在该键
                Boolean hasKey = redisTemplate.hasKey(key);
                if (hasKey == null || !hasKey) {
                    // 如果缓存中不存在该键，则将节点信息设置到Redis中，并返回节点对象
                    setNodeToRedis(datacenterId, workerId);
                    return new Node(datacenterId, workerId);
                }
            }
        }
        // 如果没有可用的节点，则返回null
        return null;
    }

    /**
     * 将节点信息设置到Redis中。
     *
     * @param datacenterId 数据中心ID
     * @param workerId     工作节点ID
     */
    private void setNodeToRedis(long datacenterId, long workerId) {
        // 将节点信息存储到Redis中
        redisTemplate.opsForValue().set(
                // 使用缓存键作为键名，节点信息作为键值
                CacheKey.key(datacenterId, workerId), List.of(datacenterId, workerId),
                // 设置过期时间
                timeoutHour, TimeUnit.HOURS);
    }

    /**
     * 节点信息
     *
     * @param datacenterId 数据中心ID。
     * @param workerId     工作节点ID。
     */
    protected record Node(long datacenterId, long workerId) {

    }

    /**
     * 缓存键。
     */
    private static class CacheKey extends CacheKeyRule {

        /**
         * 缓存键前缀。
         */
        private final static String PREFIX = "SNOWFLAKE";

        /**
         * 缓存键子标识。
         */
        private final static String SUB_ID = "NODE";

        /**
         * 数据中心ID在缓存键中的索引。
         */
        private final static int DATACENTER_ID_INDEX = 2;

        /**
         * 工作节点ID在缓存键中的索引。
         */
        private final static int WORKER_ID_INDEX = 3;

        /**
         * 缓存键中项的数量。
         */
        private final static int ITEM_COUNT = 4;

        /**
         * 根据数据中心ID和工作节点ID生成缓存键。
         *
         * @param datacenterId 数据中心ID
         * @param workerId     工作节点ID
         * @return 生成的缓存键
         */
        public static String key(long datacenterId, long workerId) {
            return key(PREFIX, String::valueOf, SUB_ID, datacenterId, workerId);
        }

        /**
         * 通用缓存键。
         *
         * @return 通用缓存键
         */
        public static String commonKey() {
            return key(PREFIX, SUB_ID, "*");
        }

    }

}
