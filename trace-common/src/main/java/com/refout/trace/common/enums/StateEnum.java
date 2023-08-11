package com.refout.trace.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 状态枚举
 *
 * @author oo w
 * @version 1.0
 * @since 2023/6/29 4:49
 */
@Getter
@AllArgsConstructor
public enum StateEnum implements AbstractDbEnum {

	/**
	 * 正常状态
	 */
	NORMAL("0", "正常"),

	/**
	 * 停用状态
	 */
	DEACTIVATE("1", "停用");

	private final String code;

	private final String info;

}
