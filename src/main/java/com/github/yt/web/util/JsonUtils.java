package com.github.yt.web.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 对象 json 字符串 相互转换
 *
 * @author sheng
 */
public class JsonUtils {

    public static String toJsonString(Object object) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();

            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("对象转换为 json string 异常", e);
        }
    }
}
