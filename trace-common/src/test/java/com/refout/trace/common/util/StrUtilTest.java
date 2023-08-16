package com.refout.trace.common.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

public class StrUtilTest {

    @Test
    public void testHasTextAll() {
        // Positive test case
        Assertions.assertTrue(StrUtil.hasTextAll("abc", "def", "123"));
        // Negative test case
        Assertions.assertFalse(StrUtil.hasTextAll("", "def", "123"));
    }

    @Test
    public void testContainsWhitespaceAll() {
        // Positive test case
        Assertions.assertTrue(StrUtil.containsWhitespaceAll("abc ", "def ", "123 "));
        // Negative test case
        Assertions.assertFalse(StrUtil.containsWhitespaceAll("abc", "def", "123"));
    }

    @Test
    public void testSubstring() {
        // Positive test case
        Assertions.assertEquals("bcd", StrUtil.substring("abcdef", 1, 4));
        // Negative test case
        Assertions.assertEquals("", StrUtil.substring(null, 1, 4));
    }

    @Test
    public void testStringUtils() {
        // 测试 substring 方法
        String str = "Hello, World!";
        String sub = StrUtil.substring(str, 7, 12);
        Assertions.assertEquals("World", sub);

        // 测试 multipleAny 方法
        boolean any = StrUtil.multipleAny(StringUtils::hasText, "Hello", "", "World");
        Assertions.assertTrue(any);

        // 测试 multipleAll 方法
        boolean all = StrUtil.multipleAll(StringUtils::hasText, "Hello", "World");
        Assertions.assertTrue(all);

        // 测试 convertToSnakeCase 方法
        String snakeCase = StrUtil.convertToSnakeCase("helloWorld");
        Assertions.assertEquals("hello_world", snakeCase);
    }

}