package com.refout.trace.common.util;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.StringUtils;

import java.util.function.Function;

/**
 * 字符串工具类，继承自Spring的StringUtils
 *
 * @author oo w
 * @version 1.0
 * @since 2023/8/4 10:22
 */
@SuppressWarnings("unused")
public class StrUtil extends StringUtils {

	/**
	 * 空字符串
	 */
	private static final String EMPTY_STR = "";

	/**
	 * 判断多个字符串是否都有值
	 *
	 * @param str 多个字符串
	 * @return 是否都有值
	 */
	public static boolean hasTextAll(String... str) {
		return multipleAll(StringUtils::hasText, str);
	}

	/**
	 * 判断多个字符串是否都包含空格
	 *
	 * @param str 多个字符串
	 * @return 是否都包含空格
	 */
	public static boolean containsWhitespaceAll(String... str) {
		return multipleAll(StringUtils::containsWhitespace, str);
	}

	/**
	 * 截取字符串
	 *
	 * @param str   字符串
	 * @param start 开始
	 * @param end   结束
	 * @return 截取结果
	 */
	public static String substring(final String str, int start, int end) {
		if (str == null) {
			return EMPTY_STR;
		}
		if (end < 0) {
			end = str.length() + end;
		}
		if (start < 0) {
			start = str.length() + start;
		}
		if (end > str.length()) {
			end = str.length();
		}
		if (start > end) {
			return EMPTY_STR;
		}
		if (start < 0) {
			start = 0;
		}
		if (end < 0) {
			end = 0;
		}
		return str.substring(start, end);
	}

	/**
	 * 判断多个字符串是否有一个有值
	 *
	 * @param function 判断函数
	 * @param str      多个字符串
	 * @return 是否有一个有值
	 */
	private static @NotNull Boolean multipleAny(
			Function<CharSequence, Boolean> function, CharSequence... str) {
		if (str == null) {
			return false;
		}
		for (CharSequence s : str) {
			if (function.apply(s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断多个字符串是否都有值
	 *
	 * @param function 判断函数
	 * @param str      多个字符串
	 * @return 是否都有值
	 */
	private static @NotNull Boolean multipleAll(
			Function<CharSequence, Boolean> function, CharSequence... str) {
		if (str == null) {
			return false;
		}
		for (CharSequence s : str) {
			if (!function.apply(s)) {
				return false;
			}
		}
		return true;
	}

}