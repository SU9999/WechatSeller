package com.su.controller;

import com.lly835.bestpay.model.PayResponse;
import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.service.OrderService;
import com.su.service.PayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PostRemove;
import java.util.Map;

/**
 * 支付模块的前端控制器
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    private Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private  OrderService orderService;

    @Autowired
    private PayService payService;

    @RequestMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String, Object> model){
        // 查询订单
        OrderDTO orderDTO = orderService.findOne(orderId);
        if (orderDTO == null){
            log.error("【支付】支付错误，没有改订单，orderId={}", orderId);
            throw new SellException(ResultStatusEnum.ORDER_NOT_EXIST);
        }

        // 发起支付: 通过PayService的create发起支付，支付订单为orderDTO
        // TODO 实际支付接口：此处需要进行微信支付验证
//        PayResponse payResponse = payService.create(orderDTO);
        // TODO 测试用例；因为没有微信验证
        PayResponse payResponse = new PayResponse();
        payResponse.setAppId("myAppId--sutong");
        payResponse.setNonceStr("myNonceStr--sutong");
        payResponse.setPackAge("myPackAge--sutong");
        payResponse.setPaySign("myPageSign--sutong");
        payResponse.setTimeStamp(System.currentTimeMillis()+"");
        model.put("payResponse", payResponse);

        model.put("returnUrl", returnUrl);
        return new ModelAndView("pay/create", model);
    }

    /**
     * 处理微信支付的异步通知：微信方在接收到支付后，会给前端发出一个ok的消息，但该消息并不可靠
     * 同时给后台发送一个异步通知：传入一个xml格式的数据，后台需要处理该异步通知，完成支付操作
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String  notifyData){

        // 处理异步通知：在payService层进行逻辑处理
        PayResponse payResponse = payService.notify(notifyData);

        // 返回给微信端处理结果
        return new ModelAndView("pay/success");
    }
}
