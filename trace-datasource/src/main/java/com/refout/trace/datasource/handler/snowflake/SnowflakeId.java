package com.refout.trace.datasource.handler.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
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
public class SnowflakeId implements IdentifierGenerator {

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

    /**
     * Generate a new identifier.
     *
     * @param session The session from which the request originates
     * @param object  the entity or collection (idbag) for which the id is being generated
     * @return a new identifier
     * @throws HibernateException Indicates trouble generating the identifier
     */
    @Override
    public Object generate(SharedSessionContractImplementor session, Object object) {
        return nextId();
    }

}
