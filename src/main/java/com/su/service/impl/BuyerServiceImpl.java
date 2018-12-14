package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.service.BuyerService;
import com.su.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class BuyerServiceImpl implements BuyerService {

    private Logger log = LoggerFactory.getLogger(BuyerServiceImpl.class);

    @Autowired
    private OrderService orderService;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {

        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if (orderDTO == null){
            log.error("【取消订单】订单不存在，orderId={}", orderId);
            throw new SellException(ResultStatusEnum.ORDER_NOT_EXIST);
        }
        return orderService.cancel(orderDTO);
    }

    // 公用方法，做安全性校验逻辑
    private OrderDTO checkOrderOwner(String openid, String orderId){
        // 根据orderId从数据库中取出数据
        OrderDTO orderDTO = orderService.findOne(orderId);
        // 对用户的权限进行校验：就是判断当前openid和该订单的openid是否相同
        if (!openid.equalsIgnoreCase(orderDTO.getBuyerOpenid())){
            log.error("【查询订单】订单的openid不一致，openid={}", openid);
            throw new SellException(ResultStatusEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
