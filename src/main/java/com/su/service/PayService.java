package com.su.service;

import com.lly835.bestpay.model.PayResponse;
import com.su.dto.OrderDTO;

/**
 * 支付Service
 */
public interface PayService {

    // 创建支付请求
    PayResponse create(OrderDTO orderDTO);

    // 处理异步通知
    PayResponse notify(String notifyData);

    // 退款
    void reFund(OrderDTO orderDTO);
}
