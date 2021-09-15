package com.taskagile.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Writer;

public final class JsonUtils {

    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {}

    public static String toJson(Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException exception) {
            throw new RuntimeException("JSON 변환 실패 !", exception);
        }
    }

    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return new ObjectMapper().readValue(json, clazz);
        } catch (IOException e) {
            log.error("Object 변환 실패 ! '" + json + "' class '" + clazz.getName() + "'", e);
            return null;
        }
    }

    public static void write(Writer writer, Object value) throws IOException {
        new ObjectMapper().writeValue(writer, value);
    }
}
