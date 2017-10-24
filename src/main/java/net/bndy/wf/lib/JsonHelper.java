/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
	
	private static ObjectMapper objMapper = new ObjectMapper();
	
	static {
		objMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objMapper.configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, false);
		objMapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
		objMapper.configure(DeserializationFeature.USE_JAVA_ARRAY_FOR_JSON_ARRAY, true);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
		objMapper.setDateFormat(df);
	}

    public static String toString(Object obj) throws JsonProcessingException {
        return objMapper.writeValueAsString(obj);
    }
    
    public static <T> T parse(String json, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
    	return objMapper.readValue(json, valueType);
    }
    
    public static void save2File(Object obj, String filepath) throws JsonGenerationException, JsonMappingException, IOException {
    	File file = new File(filepath);
    	file.getParentFile().mkdirs();
    	objMapper.writeValue(file, obj);
    }
    
    public static <T> T parseFromFile(String filepath, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
    	return objMapper.readValue(filepath, valueType);
    }
}
