package com.su.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Json格式化工具类
 */
public class JsonUtil {

    /**
     * 将对象格式化为json字符串
     * @param object
     * @return
     */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
