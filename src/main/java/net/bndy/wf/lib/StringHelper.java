/*******************************************************************************
 * Copyright (C) 2017 http://bndy.net
 * Created by Bendy (Bing Zhang)
 ******************************************************************************/
package net.bndy.wf.lib;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.xerces.impl.dv.util.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class StringHelper {

	public static String generateUUID() {
		return UUID.randomUUID().toString();
	}

	public static String generateRandomString(int length) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] token = new byte[length / 2];
		secureRandom.nextBytes(token);
		return new BigInteger(1, token).toString(16).toUpperCase();
	}

	public static String cut(String source, int length) {
		if (source.length() <= length) {
			return source;
		} else {
			return source.substring(0, length - 1) + "...";
		}
	}

	public static String capitalize(String value) {
		if (value != null && !"".equals(value)) {
			return value.substring(0, 1).toUpperCase() + value.substring(1);
		}

		return value;
	}

	public static String formatDateTime(Date date, String format) {
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.format(date);
	}

	public static Date parseDate(String dateString, String format) throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat(format);
		return ft.parse(dateString);
	}

	public static void appendToFile(String content, String filename) throws IOException {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new FileWriter(filename, true));
			out.write(content);
		} catch (IOException e) {
			throw e;
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	public static String[] splitWithoutWhitespace(String source, String separator) {
		List<String> result = new ArrayList<String>();
		String[] items = source.split(separator);
		for(String s: items) {
			if(!"".equals(s.trim())) {
				result.add(s.trim());
			}
		}
		
		return result.toArray(new String[result.size()]);
	}
	
	public static String encodeBase64(String source) throws UnsupportedEncodingException {
		return Base64.encode(source.getBytes("UTF-8"));
	}
	
	public static String decodeBase64(String base64String) throws UnsupportedEncodingException {
		return new String(Base64.decode(base64String), "UTF-8");
	}
	
	public static <T> T toJson(String content, Class<T> valueType) throws JsonParseException, JsonMappingException, IOException {
		return JsonHelper.parse(content, valueType);
	}
}
