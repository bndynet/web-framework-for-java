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
import java.util.*;

import com.google.common.primitives.Longs;
import org.apache.xerces.impl.dv.util.Base64;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class StringHelper {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String generateRandomString(int length) {
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        int count = chars.length();
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(count)));
        }

        return sb.toString();
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
        for (String s : items) {
            if (!"".equals(s.trim())) {
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

    public static String insertUnderscoreBetweenWords(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < buf.length() - 1; i++) {
            if (
                Character.isLowerCase(buf.charAt(i - 1)) &&
                    Character.isUpperCase(buf.charAt(i)) &&
                    Character.isLowerCase(buf.charAt(i + 1))
                ) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString();
    }

    public static String toLowerCase(String text) {
        return text.toLowerCase(Locale.ROOT);
    }

    public static String insertBefore(String source, int index, String content) {
        if (source == null)
            source = "";

        if (index <= 0) {
            return content + source;
        }

        if (index >= source.length()) {
            return source + content;
        }

        return source.substring(0, index) + content + source.substring(index);
    }

    public static String insertAfter(String source, int index, String content) {
        if (source == null)
            source = "";

        if (index < 0) {
            return content + source;
        }

        if (index + 1 >= source.length()) {
            return source + content;
        }

        return source.substring(0, index + 1) + content + source.substring(index + 1);
    }

    public static List<String> stairSplit(String source, String separator) {
        List<String> result = new ArrayList();
        String path = null;
        for (String item: source.split(separator)) {
            if (path == null) {
                path = item;
            } else {
                path += separator + item;
            }
            result.add(path);
        }
        return result;
    }

    public static List<Long> splitToLong(String source, String separator) {
        List<Long> result = new ArrayList<>();
        String[] tmp = splitWithoutWhitespace(source, separator);
        for (String s: tmp) {
            result.add(Long.parseLong(s));
        }

        return result;
    }
}
