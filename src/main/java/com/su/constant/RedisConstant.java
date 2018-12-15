package com.su.constant;

/**
 *  Redis配置的常量
 */
public interface RedisConstant {
    String TOKEN_PREFIX = "token_%s"; // 配置redis中存放的token的前缀
    Integer EXPIRE = 7200; // 2小时
}
