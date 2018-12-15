package com.su.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class CookieUtil {
    /**
     * 向响应中写入cookie
     * @param response
     * @param key
     * @param value
     * @param expire
     */
    public static void setCookie(HttpServletResponse response,
                                 String key, String value, Integer expire){
        Cookie cookie = new Cookie(key, value);
        cookie.setPath("/");
        cookie.setMaxAge(expire);

        response.addCookie(cookie);
    }

    /**
     * 从request中获取cookie
     * @param request
     * @param name
     * @return
     */
    public static Cookie getCookie(HttpServletRequest request, String name){
        Map<String, Cookie> map = readCookieMap(request);
        return map.get(name);
    }

    /**
     * 将request中的所有cookie转换成一个map
     * @param request
     * @return
     */
    private static Map<String, Cookie> readCookieMap(HttpServletRequest request){
        Map<String, Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (null != cookies){
            for (Cookie cookie: cookies){
                map.put(cookie.getName(), cookie);
            }
        }
        return map;
    }
}
