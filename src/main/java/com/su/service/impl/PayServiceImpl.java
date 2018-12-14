package com.su.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.BestPayService;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.service.OrderService;
import com.su.service.PayService;
import com.su.utils.JsonUtil;
import com.su.utils.MathUtil;
import org.hibernate.hql.internal.ast.tree.SelectExpressionList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;

@Service
public class PayServiceImpl implements PayService {

    private final String ORDER_NAME = "微信点餐订单";
    private Logger log = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {

        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信支付】请求支付：payRequest={}", JsonUtil.toJson(payRequest));
        // TODO 此处验证不通过：java.lang.RuntimeException:
        //  【微信统一支付】发起支付, returnCode != SUCCESS, returnMsg = 商户号mch_id与appid不匹配
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】返回支付：payResponse={}", JsonUtil.toJson(payResponse));

        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        /**
         *  一、验证签名：best-Pay-SDK已经完成
         *  二、支付的状态：best-Pay-SDK已经完成
         *  三、支付金额
         *  四、支付人（下单人 == 支付人）
         */
        // 使用第三方SDK进异步通知的处理：best-pay-sdk
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);

        log.info("【微信支付】异步通知：payResponse={}", JsonUtil.toJson(payResponse));

        // 修改数据库中的订单的支付状态
        // 第一步：查询订单
        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        // 判断订单是否存在
        if (orderDTO == null){
            log.error("【微信支付】订单不存在：orderId={}", payResponse.getOrderId());
            throw new SellException(ResultStatusEnum.ORDER_NOT_EXIST);
        }
        // 判断金额是否一致
        // 注意：此处比较金额是否相等不能够直接使用compareTo，或equals，
        // 应该使用两个浮点数的差值和一个精度（比如0.01）进行比较
        if (!MathUtil.equals(payResponse.getOrderAmount(), orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】支付金额不正确：orderId={},微信通知金额={},系统（数据库）金额={}",
                    payResponse.getOrderId(), payResponse.getOrderAmount(), orderDTO.getOrderAmount());
            throw new SellException(ResultStatusEnum.WECHAT_PAY_NOTIFY_ERROR);
        }
        // 修改支付状态
        orderService.pay(orderDTO);
        return payResponse;
    }

    @Override
    public void reFund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);

        log.info("【微信退款】request={}", JsonUtil.toJson(refundRequest));
        // TODO 此处退款需要验证证书：java.lang.RuntimeException: 读取微信商户证书文件出错，
        //  Caused by: java.io.FileNotFoundException: /var/weixin_cret/h5.p12
        //  (No such file or directory)---该证书需要注册微信公众平台，在商户平台获取下载，
        //  并在配置（application.yml)文件中进行配置路径
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}", JsonUtil.toJson(refundResponse));
    }
}
