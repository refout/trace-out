package com.refout.trace.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/1 14:15
 */
public class DateUtil {

	public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

	public static final String DATE_PATTERN = "yyyy-MM-dd";

	public static final String TIME_PATTERN = "HH:mm:ss.SSS";

	public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);
	public static final ZoneId ZONE_ID = ZoneId.systemDefault();

	public static Date toDate(LocalDateTime localDateTime) {
		ZonedDateTime zdt = localDateTime.atZone(ZONE_ID);
		return Date.from(zdt.toInstant());
	}

	public static LocalDateTime toLocalDateTime(Date date) {
		return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
	}

	public static LocalDateTime toLocalDateTime(String dateString) {
		return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
	}

	public static LocalDate toLocalDate(String dateString) {
		return LocalDate.parse(dateString, DATE_FORMATTER);
	}

}
