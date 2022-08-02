package com.dhais.tqb.common.utils;

import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * All rights Reserved, Designed By Fan Jun
 *
 * @author Fan Jun
 * @version 1.0
 * @since 2022/6/20 11:52
 */
public class PropertiesUtil {
    private static final Map<String,String> propertiesMap = new HashMap<>();

    public static Map<String, String> getProperties() {return propertiesMap;}

    public static String getString(String key){
        try {
            return propertiesMap.get(key);
        } catch (Exception e) {
            return null;
        }
    }

    public static String getString(String key,String defaultValue){
        try {
            String value = propertiesMap.get(key);
            if (StrUtil.isBlank(value)) {
                return defaultValue;
            }
            return value;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Integer getInt(String key){
        String value = propertiesMap.get(key);
        if (StrUtil.isBlank(value)) {
            return null;
        }
        return Integer.parseInt(value);
    }

    public static Integer getInt(String key,int defaultValue){
        String value = propertiesMap.get(key);
        if (StrUtil.isBlank(value)) {
            return defaultValue;
        }
        return Integer.parseInt(value);
    }

    public static long getLong(String key,long defaultValue){
        String value = propertiesMap.get(key);
        if (StrUtil.isBlank(value)) {
            return defaultValue;
        }
        try {
            return Long.parseLong(value.trim());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static boolean getBoolean(String key){
        String value = propertiesMap.get(key);
        if (StrUtil.isBlank(value)) {
            return false;
        }
        return Boolean.parseBoolean(value.trim());
    }

    public static boolean getBoolean(String key,boolean defaultValue){
        String value = propertiesMap.get(key);
        if (StrUtil.isBlank(value)) {
            return defaultValue;
        }
        return Boolean.parseBoolean(value.trim());
    }
}
