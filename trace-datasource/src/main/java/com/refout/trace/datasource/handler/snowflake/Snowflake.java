package com.refout.trace.datasource.handler.snowflake;

/**
 * 参考Twitter Snowflake算法实现
 *
 * <p>
 * ID结构：<br/>
 * 0-41位：时间戳，精确到毫秒级，初始时间戳为twepoch变量的值<br/>
 * 42-46位：数据中心ID，取值范围为0-31<br/>
 * 47-51位：机器ID，取值范围为0-31<br/>
 * 52-63位：序列号，取值范围为0-4095<br/>
 * </p>
 *
 * <p>
 * 注意：该实现中未使用时钟回拨检测和自适应调整机器ID和数据中心ID的功能，
 * 在使用过程中需要根据实际情况进行修改。
 * </p>
 *
 * @see <a href="https://github.com/twitter-archive/snowflake/tree/snowflake-2010">Snowflake官方文档</a>
 */

public class Snowflake {

	/**
	 * 起始时间戳，用于将时间戳调整到更小的值，避免ID过大
	 */
	private static final long TWEPOCH = 1288834974657L;

	/**
	 * 机器ID所占位数，取值范围为0-31
	 */
	private static final long WORKER_ID_BITS = 5L;

	/**
	 * 数据中心ID所占位数，取值范围为0-31
	 */
	private static final long DATACENTER_ID_BITS = 5L;

	/**
	 * 序列号所占位数，取值范围为0-4095
	 */
	private static final long SEQUENCE_BITS = 12L;

	/**
	 * 最大机器ID，取值范围为0-31
	 */
	private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);

	/**
	 * 最大数据中心ID，取值范围为0-31
	 */
	private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);

	/**
	 * 机器ID左移位数，取决于序列号和数据中心ID位数
	 */
	private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;

	/**
	 * 数据中心ID左移位数，取决于序列号位数
	 */
	private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS;

	/**
	 * 时间戳左移位数，取决于序列号、机器ID和数据中心ID位数
	 */
	private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS;

	/**
	 * 序列号掩码，取值范围为0-4095，用于限制序列号值在该范围内
	 */
	private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

	/**
	 * 机器ID，取值范围为0-31
	 */
	private final long workerId;

	/**
	 * 数据中心ID，取值范围为0-31
	 */
	private final long datacenterId;

	/**
	 * 上次生成ID的时间戳
	 */
	private long lastTimestamp = -1L;

	/**
	 * 当前序列号
	 */
	private long sequence = 0L;

	/**
	 * 构造函数
	 *
	 * @param workerId     机器ID，取值范围为0-31
	 * @param datacenterId 数据中心ID，取值范围为0-31
	 * @throws IllegalArgumentException 当机器ID或数据中心ID超出取值范围时抛出异常
	 */
	public Snowflake(long datacenterId, long workerId) {
		checked(workerId, datacenterId);
		this.workerId = workerId;
		this.datacenterId = datacenterId;
	}

	/**
	 * 检查workerId和datacenterId的输入。
	 *
	 * @param workerId     机器ID，取值范围为0-31
	 * @param datacenterId 数据中心ID，取值范围为0-31
	 * @throws IllegalArgumentException 如果workerId大于maxWorkerId或小于0，
	 *                                  并且如果datacenterId大于maxDatacenterId或小于0，
	 *                                  则会抛出IllegalArgumentException异常。
	 */
	private void checked(long workerId, long datacenterId) {
		if (workerId > MAX_WORKER_ID || workerId < 0) {
			throw new IllegalArgumentException("workerId不能大于" + MAX_WORKER_ID + "或小于0");
		}
		if (datacenterId > MAX_DATACENTER_ID || datacenterId < 0) {
			throw new IllegalArgumentException("datacenterId不能大于" + MAX_DATACENTER_ID + "或小于0");
		}
	}

	/**
	 * 生成ID
	 *
	 * @return 全局唯一ID
	 * @throws RuntimeException 当系统时钟发生回退时抛出异常
	 */
	public synchronized long nextId() {
		long timestamp = timeGen();
		if (timestamp < lastTimestamp) { // 时间回退了，抛出异常
			throw new RuntimeException("系统时钟发生回退，无法生成ID");
		}
		if (lastTimestamp == timestamp) { // 时间相同，自增序列号
			sequence = (sequence + 1) & SEQUENCE_MASK;
			if (sequence == 0) { // 序列号用完了，等待下一毫秒
				timestamp = tilNextMillis(lastTimestamp);
			}
		} else { // 时间变化，重置序列号
			sequence = 0L;
		}
		lastTimestamp = timestamp;
		return ((timestamp - TWEPOCH) << TIMESTAMP_LEFT_SHIFT) // 时间戳部分
				| (datacenterId << DATACENTER_ID_SHIFT) // 数据中心ID部分
				| (workerId << WORKER_ID_SHIFT) // 机器ID部分
				| sequence; // 序列号部分
	}

	/**
	 * 获取下一毫秒时间戳
	 *
	 * @param lastTimestamp 上次生成ID的时间戳
	 * @return 下一毫秒时间戳
	 */
	protected long tilNextMillis(long lastTimestamp) {
		long timestamp = timeGen();
		while (timestamp <= lastTimestamp) {
			timestamp = timeGen();
		}
		return timestamp;
	}

	/**
	 * 获取当前时间戳
	 *
	 * @return 当前时间戳
	 */
	protected long timeGen() {
		return System.currentTimeMillis();
	}

	/**
	 * 根据Snowflake的ID，获取生成时间
	 *
	 * @param id snowflake算法生成的id
	 * @return 生成的时间
	 */
	public long getGenerateDateTime(long id) {
		return (id >> TIMESTAMP_LEFT_SHIFT & ~(-1L << 41L)) + TWEPOCH;
	}

}