package com.refout.trace.common.util;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class JsonUtilTest {

    @Test
    public void testToJson() {
        TestData testData = new TestData("John", 25);
        String json = JsonUtil.toJson(testData);
        Assertions.assertNotNull(json);
        Assertions.assertEquals("{\"name\":\"John\",\"age\":25}", json);
    }

    @Test
    public void testFromJson() {
        String json = "{\"name\":\"John\",\"age\":25}";
        TestData testData = JsonUtil.fromJson(json, TestData.class);
        Assertions.assertNotNull(testData);
        Assertions.assertEquals("John", testData.getName());
        Assertions.assertEquals(25, testData.getAge());
    }

    @Test
    public void testFromJsonToMap() {
        String json = "{\"name\":\"John\",\"age\":25}";
        Map<String, Object> map = JsonUtil.fromJsonToMap(json, Object.class);
        Assertions.assertNotNull(map);
        Assertions.assertEquals("John", map.get("name"));
        Assertions.assertEquals(25, map.get("age"));
    }

    @Test
    public void testFromJsonToList() {
        String json = "[{\"name\":\"John\",\"age\":25},{\"name\":\"Jane\",\"age\":30}]";
        List<TestData> dataList = JsonUtil.fromJsonToList(json, TestData.class);
        Assertions.assertNotNull(dataList);
        Assertions.assertEquals(2, dataList.size());
        Assertions.assertEquals("John", dataList.get(0).getName());
        Assertions.assertEquals(25, dataList.get(0).getAge());
        Assertions.assertEquals("Jane", dataList.get(1).getName());
        Assertions.assertEquals(30, dataList.get(1).getAge());
    }

    @Test
    public void testToJsonObject() {
        String json = "{\"name\":\"John\",\"age\":25}";
        JsonNode jsonNode = JsonUtil.toJsonObject(json);
        Assertions.assertNotNull(jsonNode);
        Assertions.assertEquals("John", jsonNode.get("name").asText());
        Assertions.assertEquals(25, jsonNode.get("age").asInt());
    }

    @Test
    public void testToJsonNullObject() {
        TestData testData = null;
        String json = JsonUtil.toJson(testData);
        Assertions.assertNull(json);
    }

    @Test
    public void testFromJsonEmptyJson() {
        String json = "";
        TestData testData = JsonUtil.fromJson(json, TestData.class);
        Assertions.assertNull(testData);
    }

    @Test
    public void testFromJsonInvalidJson() {
        String json = "{\"name\":\"John\",\"age\":25";
        TestData testData = JsonUtil.fromJson(json, TestData.class);
        Assertions.assertNull(testData);
    }

    @Test
    public void testFromJsonToMapEmptyJson() {
        String json = "";
        Map<String, Object> map = JsonUtil.fromJsonToMap(json, Object.class);
        Assertions.assertNull(map);
    }

    @Test
    public void testFromJsonToListEmptyJson() {
        String json = "";
        List<TestData> dataList = JsonUtil.fromJsonToList(json, TestData.class);
        Assertions.assertNull(dataList);
    }

    @Test
    public void testToJsonObjectEmptyJson() {
        String json = "";
        JsonNode jsonNode = JsonUtil.toJsonObject(json);
        Assertions.assertNull(jsonNode);
    }

    @Test
    public void testToJsonObjectInvalidJson() {
        String json = "{\"name\":\"John\",\"age\":25";
        JsonNode jsonNode = JsonUtil.toJsonObject(json);
        Assertions.assertNull(jsonNode);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class TestData {

        private String name;

        private int age;

    }

}