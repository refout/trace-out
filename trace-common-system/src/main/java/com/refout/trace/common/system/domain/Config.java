package com.refout.trace.common.system.domain;

import com.refout.trace.common.system.repository.convert.HistoryValueConvert;
import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 配置信息
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/10 13:24
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "ts_config")
@Entity
public class Config extends AbstractEntity {

	/**
	 * 配置名称
	 */
	@Column(name = "name", nullable = false, length = 128)
	private String name;

	/**
	 * 配置值
	 */
	@Column(name = "value", nullable = false, length = 512)
	private Object value;

	/**
	 * 配置所属功能
	 */
	@Column(name = "belong", nullable = false, length = 64)
	private String belong;

	/**
	 * 配置所属应用,common表示所有应用可用
	 */
	@Column(name = "app", nullable = false, length = 64, columnDefinition = "varchar(64) default 'common'")
	private String app;

	/**
	 * 历史配置:[{value:1,time:2023-08-08 08:08:08 888}]
	 */
	@Convert(converter = HistoryValueConvert.class)
	@Column(name = "history_value", columnDefinition = "json")
	private HistoryValue historyValue;

	/**
	 * 备注
	 */
	@Column(name = "remark", nullable = false, length = 512)
	private String remark;

	/**
	 * 历史配置
	 *
	 * @param value 值
	 * @param time  时间
	 */
	public record HistoryValue(String value, LocalDateTime time){}

}
