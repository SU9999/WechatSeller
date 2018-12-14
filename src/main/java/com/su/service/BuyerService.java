package com.su.service;

import com.su.dto.OrderDTO;
import org.springframework.data.domain.jaxb.SpringDataJaxb;

/**
 * 用于做与买家相关的业务
 */
public interface BuyerService {

    // 根据openid和orderId查询一个订单，openid作为安全性校验
    public OrderDTO findOrderOne(String openid, String orderId);

    // 根据openid和orderId取消一个订单，openid作为安全性校验
    public OrderDTO cancelOrder(String openid, String orderId);
}
