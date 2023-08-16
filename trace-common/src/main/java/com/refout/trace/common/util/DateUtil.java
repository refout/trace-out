package com.refout.trace.common.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
/**
 * 日期工具类，提供日期转换的方法。
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/1 14:15
 */
public class DateUtil {

    /**
     * 日期时间的格式模式：yyyy-MM-dd HH:mm:ss.SSS
     */
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";

    /**
     * 日期的格式模式：yyyy-MM-dd
     */
    public static final String DATE_PATTERN = "yyyy-MM-dd";

    /**
     * 时间的格式模式：HH:mm:ss.SSS
     */
    public static final String TIME_PATTERN = "HH:mm:ss.SSS";

    /**
     * 日期时间格式化器，用于将日期时间格式化为字符串
     */
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    /**
     * 日期格式化器，用于将日期格式化为字符串
     */
    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);

    /**
     * 时间格式化器，用于将时间格式化为字符串
     */
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_PATTERN);

    /**
     * 系统默认时区
     */
    public static final ZoneId ZONE_ID = ZoneId.systemDefault();

    /**
     * 表示时区偏移量的常量
     * +8 代表东八区
     */
    public static final ZoneOffset offset = ZoneOffset.of("+8");

    /**
     * 将 LocalDateTime 转换为 Date 对象
     *
     * @param localDateTime 要转换的 LocalDateTime 对象
     * @return 转换后的 Date 对象
     */
    public static @NotNull Date toDate(@NotNull LocalDateTime localDateTime) {
        ZonedDateTime zdt = localDateTime.atZone(ZONE_ID);
        return Date.from(zdt.toInstant());
    }

    /**
     * 将 Date 对象转换为 LocalDateTime 对象
     *
     * @param date 要转换的 Date 对象
     * @return 转换后的 LocalDateTime 对象
     */
    public static LocalDateTime toLocalDateTime(@NotNull Date date) {
        return date.toInstant().atZone(ZONE_ID).toLocalDateTime();
    }

    /**
     * 将字符串转换为 LocalDateTime 对象
     *
     * @param dateString 要转换的字符串
     * @return 转换后的 LocalDateTime 对象
     */
    @Contract(pure = true)
    public static @NotNull LocalDateTime toLocalDateTime(String dateString) {
        return LocalDateTime.parse(dateString, DATE_TIME_FORMATTER);
    }

    /**
     * 将字符串转换为 LocalDate 对象
     *
     * @param dateString 要转换的字符串
     * @return 转换后的 LocalDate 对象
     */
    @Contract(pure = true)
    public static @NotNull LocalDate toLocalDate(String dateString) {
        return LocalDate.parse(dateString, DATE_FORMATTER);
    }

    public static @NotNull LocalDateTime timestampToLocalDateTime(long timestamp) {
        Instant instant = Instant.ofEpochMilli(timestamp);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long LocalDateTimeToTimestamp(@NotNull LocalDateTime time) {
        return time.toInstant(offset).toEpochMilli();
    }

}