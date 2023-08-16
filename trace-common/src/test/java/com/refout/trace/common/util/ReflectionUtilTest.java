package com.refout.trace.common.util;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReflectionUtilTest {

    @Test
    public void testGetFields() {
        // 调用 getFields 方法获取 ExampleClass 类中的所有字段
        Field[] fields = ReflectionUtil.getFields(ExampleClass.class, field -> true);

        // 断言获取到的字段数组长度为 3
        assertEquals(3, fields.length);

        // 断言字段名称和类型符合预期
        assertEquals("name", fields[0].getName());
        assertEquals(String.class, fields[0].getType());

        assertEquals("age", fields[1].getName());
        assertEquals(int.class, fields[1].getType());

        assertEquals("active", fields[2].getName());
        assertEquals(boolean.class, fields[2].getType());
    }

    @Test
    public void testGetFieldsByType() {
        // 创建一个示例类用于测试

        // 调用 getFields 方法获取 ExampleClass 类中类型为 String 的字段
        Field[] fields = ReflectionUtil.getFields(ExampleClass.class, String.class);

        // 断言获取到的字段数组长度为 1
        assertEquals(1, fields.length);

        // 断言字段名称和类型符合预期
        assertEquals("name", fields[0].getName());
        assertEquals(String.class, fields[0].getType());
    }

    static class ExampleClass {

        public int age;

        protected boolean active;

        private String name;

    }

}