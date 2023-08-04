package com.refout.trace.common.util;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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

}