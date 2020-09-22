package com.dyp.dashboard.util;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class JsonUtils {
	
	private static Logger logger = LoggerFactory.getLogger(JsonUtils.class);

    private final static ObjectMapper objectMapper = new ObjectMapper();
    private final static ObjectMapper JSON = new ObjectMapper();

    static {
    	JSON.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JsonUtils() {

    }

    public static ObjectMapper getInstance() {

        return objectMapper;
    }

    /**
     * javaBean,list,array convert to json string
     */
    public static String obj2json(Object obj) {
        String rString = null;
        try {
			rString = objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error("json parse error!",e);
		}
    	return rString;
    }

    /**
     * json string convert to javaBean
     * @throws JsonMappingException 
     */
    public static <T> T json2pojo(String jsonStr, Class<T> clazz) throws JsonMappingException
            {
    	T t =null;
    	try {
			t = objectMapper.readValue(jsonStr,clazz);
		} catch (JsonParseException e) {
			logger.error("json parse error!",e);
		} catch (JsonMappingException e) {
			throw e;
		} catch (IOException e) {
			logger.error("io error!",e);
		}
        return t;
    }

    /**
     * json string convert to map
     */
    @SuppressWarnings("unchecked")
	public static Map<String, Object> json2map(String jsonStr)
            throws IOException {
        return objectMapper.readValue(jsonStr, Map.class);
    }


    /**
     * map convert to javaBean
     */
    @SuppressWarnings("rawtypes")
	public static <T> T map2pojo(Map map, Class<T> clazz) {
        return objectMapper.convertValue(map, clazz);
    }

    public static byte[] toJsonAsBytes(Object object) {
        try {
            return JSON.writeValueAsBytes(object);
        } catch (IOException e) {
        	logger.error("to json as bytes error!",e);
            return null;
        }
    }
    
}