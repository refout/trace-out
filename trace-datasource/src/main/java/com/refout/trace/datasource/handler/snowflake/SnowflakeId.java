package com.refout.trace.datasource.handler.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.jetbrains.annotations.NotNull;

/**
 * 自定义的Hibernate标识生成器，用于生成唯一的Snowflake ID。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/7/25 23:08
 */
@Slf4j
public class SnowflakeId implements IdentifierGenerator {

    /**
     * id 生成器
     */
    private static Snowflake snowflake = null;

    /**
     * 接受一个RedisSnowflakeHandler对象作为参数，用于获取数据中心ID和工作节点ID
     *
     * @param redisSnowflakeHandler {@link RedisSnowflakeHandler}
     */
    public SnowflakeId(@NotNull RedisSnowflakeHandler redisSnowflakeHandler) {
        if (snowflake == null) {
            RedisSnowflakeHandler.Node node = redisSnowflakeHandler.getNode();
            if (node == null) {
                throw new RuntimeException("datacenter_id,worker_id has been exhausted!");
            }
            snowflake = new Snowflake(node.datacenterId(), node.workerId());
            log.debug("datacenterId:{},workerId:{}", node.datacenterId(), node.workerId());
        }
    }

    /**
     * 用于直接调用Snowflake对象的nextId方法生成一个新的Snowflake ID
     *
     * @return 生成一个新的Snowflake ID
     */
    public static long nextId() {
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
