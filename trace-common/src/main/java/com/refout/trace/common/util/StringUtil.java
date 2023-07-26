package com.refout.trace.common.util;

import org.springframework.util.StringUtils;

public class StringUtil extends StringUtils {

	/**
	 * 空字符串
	 */
	private static final String EMPTY_STR = "";

	public static boolean hasTextAll(String... str) {
		if (str == null || str.length == 0) {
			return false;
		}
		for (String s : str) {
			if (!hasText(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 截取字符串
	 *
	 * @param str   字符串
	 * @param start 开始
	 * @param end   结束
	 * @return 结果
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

}
