package com.su.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 使用redis实现细粒度的所机制
 */
@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    //加锁：key=productId， value=超时时间：
    public boolean lock(String key, String value){
        // 首先判断redis数据库中有没有该key，没有，则说明可以获取锁
        if (redisTemplate.opsForValue().setIfAbsent(key, value)){
            return true;
        }

        // 当两个线程同时请求锁时，执行如下判断
        String currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue!=null && Long.parseLong(currentValue)<System.currentTimeMillis()){
            // getAndSet方法同时只有一个线程可以调用
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (oldValue!=null && oldValue.equals(currentValue)){
                return true;
            }
        }

        return false;
    }

    public void unlock(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
