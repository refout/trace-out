package com.refout.trace.common.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class DateUtilTest {

    @Test
    public void testToDate() {
        LocalDateTime localDateTime = LocalDateTime.of(2023, 5, 1, 14, 15, 30);
        Date date = DateUtil.toDate(localDateTime);
        LocalDateTime convertedLocalDateTime = DateUtil.toLocalDateTime(date);
        Assertions.assertEquals(localDateTime, convertedLocalDateTime);
    }

    @Test
    public void testToLocalDateTime() {
        Date date = new Date();
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
        Date convertedDate = DateUtil.toDate(localDateTime);
        Assertions.assertEquals(date, convertedDate);
    }

    @Test
    public void testToLocalDateTimeFromString() {
        String dateString = "2023-05-01 14:15:30.123";
        LocalDateTime localDateTime = DateUtil.toLocalDateTime(dateString);
        String convertedDateString = localDateTime.format(DateUtil.DATE_TIME_FORMATTER);
        Assertions.assertEquals(dateString, convertedDateString);
    }

    @Test
    public void testToLocalDateFromString() {
        String dateString = "2023-05-01";
        LocalDate localDate = DateUtil.toLocalDate(dateString);
        String convertedDateString = localDate.format(DateUtil.DATE_FORMATTER);
        Assertions.assertEquals(dateString, convertedDateString);
    }

    /**
     * 单元测试方法
     */
    @Test
    public void testTimestampToLocalDateTimeAndLocalDateTimeToTimestamp() {
        long timestamp = 1621234567890L;
        LocalDateTime dateTime = DateUtil.timestampToLocalDateTime(timestamp);
        long convertedTimestamp = DateUtil.LocalDateTimeToTimestamp(dateTime);

        // 断言转换前后的时间戳相等
        Assertions.assertEquals(timestamp, convertedTimestamp);
    }

}