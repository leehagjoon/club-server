package com.here.app.comn.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtils {

	public static ObjectMapper getObjectMapper() {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		return mapper;
	}

	public static String toJson(Object object) {
		String json = null;

		try {
			json = getObjectMapper().writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("", e);
		}

		return json;
	}

	public static <T> T fromJson(String val, Class<T> clazz){
		T object = null;

		try {
			object = getObjectMapper().readValue(val, clazz);
		} catch (IOException e) {
			log.error("", e);
		}
		
		return object;
	}
	
	public static <T> T fromJson(byte[] bytes, Class<T> clazz){
		T object = null;

		try {
			object = getObjectMapper().readValue(bytes, clazz);
		} catch (IOException e) {
			log.error("", e);
		}
		
		return object;
	}	

	public static <T> T fromJson(String val, TypeReference<T> type){
		T object = null;

		try {
			object = getObjectMapper().readValue(val, type);
		} catch (IOException e) {
			log.error("", e);
		}
		
		return object;
	}

	public static <T> T bytesToJson(byte[] bytes, TypeReference<T> type){
		T object = null;

		try {
			object = getObjectMapper().readValue(bytes, type);
		} catch (IOException e) {
			log.error("", e);
		}
		
		return object;
	}

}
