package com.su.service;

/**
 * 模拟秒杀的service
 */
public interface SecKillService {

    public String query(String productId);

    public void secKill(String productId);
}
