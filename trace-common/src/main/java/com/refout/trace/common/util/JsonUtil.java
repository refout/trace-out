package com.refout.trace.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.refout.trace.common.util.DateUtil.*;

/**
 * Json工具类，用于对象与JSON字符串之间的转换
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 15:43
 */
@Slf4j
public class JsonUtil {

	/**
	 * ObjectMapper对象，用于JSON序列化和反序列化
	 */
	public static final ObjectMapper mapper = new ObjectMapper();

	static {
		// 配置ObjectMapper
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		// 注册JavaTimeModule，用于处理Java 8日期时间类型
		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
		javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
		mapper.registerModule(javaTimeModule);
		// 配置ObjectMapper，忽略未知属性和空对象
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	/**
	 * 将对象转换为JSON字符串
	 *
	 * @param obj 要转换的对象
	 * @return 转换后的JSON字符串
	 */
	@Nullable
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("json转换失败", e);
			return null;
		}
	}

	/**
	 * 将JSON字符串转换为指定类型的对象
	 *
	 * @param json  JSON字符串
	 * @param clazz 对象类型
	 * @param <T>   对象类型参数
	 * @return 转换后的对象
	 */
	@Nullable
	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			log.error("json转换失败", e);
			return null;
		}
	}

	/**
	 * 将JSON字符串转换为Map类型的对象
	 *
	 * @param json  JSON字符串
	 * @param clazz Map中值的类型
	 * @param <T>   Map中值的类型参数
	 * @return 转换后的Map对象
	 */
	@Nullable
	public static <T> Map<String, T> fromJsonToMap(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			MapType valueType = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, clazz);
			return mapper.readValue(json, valueType);
		} catch (JsonProcessingException e) {
			log.error("json转换失败", e);
			return null;
		}
	}

	/**
	 * 将JSON字符串转换为List类型的对象
	 *
	 * @param json  JSON字符串
	 * @param clazz List中元素的类型
	 * @param <T>   List中元素的类型参数
	 * @return 转换后的List对象
	 */
	@Nullable
	public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			log.error("json转换失败", e);
			return null;
		}
	}

	/**
	 * 将JSON字符串转换为JsonNode对象
	 *
	 * @param json JSON字符串
	 * @return 转换后的JsonNode对象
	 */
	@Nullable
	public static JsonNode toJsonObject(String json) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			return mapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("json转换失败", e);
			return null;
		}
	}

}