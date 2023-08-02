package com.refout.trace.common.util;

import org.springframework.util.StringUtils;

import java.util.function.Function;

@SuppressWarnings("unused")
public class StrUtil extends StringUtils {

	/**
	 * 空字符串
	 */
	private static final String EMPTY_STR = "";

	public static boolean hasTextAll(String... str) {
		return multipleAll(StringUtils::hasText, str);
	}

	public static boolean containsWhitespaceAll(String... str) {
		return multipleAll(StringUtils::containsWhitespace, str);
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

	private static Boolean multipleAny(Function<CharSequence, Boolean> function, CharSequence... str) {
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

	private static Boolean multipleAll(Function<CharSequence, Boolean> function, CharSequence... str) {
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
