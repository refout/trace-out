package com.refout.trace.common.system.domain;

import com.refout.trace.common.enums.StateEnum;
import com.refout.trace.datasource.convert.enums.StateEnumConverter;
import com.refout.trace.datasource.domain.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * API实体类
 * 继承自AbstractEntity抽象类
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/14 12:56
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@Table(name = "ts_api")
@Entity
public class Api extends AbstractEntity {

	/**
	 * API名称
	 */
	@Column(name = "api_name")
	private String apiName;

	/**
	 * API路径
	 */
	@Column(name = "path")
	private String path;

	/**
	 * 状态枚举
	 */
	@Convert(converter = StateEnumConverter.class)
	@Column(name = "state")
	private StateEnum state;

	/**
	 * 权限
	 */
	@Column(name = "permission")
	private String permission;

	/**
	 * 备注
	 */
	@Column(name = "remark")
	private String remark;

}
