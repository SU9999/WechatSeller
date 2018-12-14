package com.su.utils;

import java.util.UUID;

public class KeyUtil {
    /**
     * 生成唯一的主键：使用UUID
     * 使用synchronized，避免多线程情况下产生相同的主键
     */
    public static synchronized  String getUniqueKey() {
        return UUID.randomUUID().toString().substring(0, 16);
    }

}
