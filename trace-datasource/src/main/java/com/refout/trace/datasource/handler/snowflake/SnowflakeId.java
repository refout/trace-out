package com.refout.trace.datasource.handler.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/25 23:08
 */
@Slf4j
@Component
public class SnowflakeId {

    private final Snowflake snowflake;

    public SnowflakeId(@NotNull RedisSnowflakeHandler redisSnowflakeHandler) {
        RedisSnowflakeHandler.Node node = redisSnowflakeHandler.getNode();
        if (node == null) {
            throw new RuntimeException("datacenter_id,worker_id has been exhausted!");
        }

        snowflake = new Snowflake(node.datacenterId(), node.workerId());
        log.debug("datacenterId:{},workerId:{}", node.datacenterId(), node.workerId());
    }

    public long nextId() {
        return snowflake.nextId();
    }

}
