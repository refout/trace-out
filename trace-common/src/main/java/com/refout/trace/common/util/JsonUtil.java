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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.refout.trace.common.util.DateUtil.*;

/**
 * TODO
 *
 * @author oo w
 * @version 1.0
 * @since 2023/5/12 15:43
 */

public class JsonUtil {

	public static final ObjectMapper mapper = new ObjectMapper();

	static {
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		JavaTimeModule javaTimeModule = new JavaTimeModule();
		javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
		javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DATE_FORMATTER));
		javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(TIME_FORMATTER));
		javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
		javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DATE_FORMATTER));
		javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(TIME_FORMATTER));
		mapper.registerModule(javaTimeModule);

		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	}

	public static String toJson(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> Map<String, T> fromJsonToMap(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			MapType valueType = TypeFactory.defaultInstance().constructMapType(Map.class, String.class, clazz);
			return mapper.readValue(json, valueType);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static <T> List<T> fromJsonToList(String json, Class<T> clazz) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, clazz);
			return mapper.readValue(json, javaType);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

	public static JsonNode toJsonObject(String json) {
		if (json == null || json.isBlank()) {
			return null;
		}
		try {
			return mapper.readTree(json);
		} catch (JsonProcessingException e) {
			return null;
		}
	}

}
