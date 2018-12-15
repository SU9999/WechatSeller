package com.su.enums;

import lombok.Getter;

import javax.persistence.Id;

/** 返回结果集的枚举 */
@Getter
public enum ResultStatusEnum {

    SUCCESS(0, "成功"),
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_QUANTITY_ERROR(11, "商品数量不正确"),
    PRODUCT_STOCK_ERROR(12,"库存不足"),
    ORDER_NOT_EXIST(13, "订单不存在"),
    DETAIL_NOT_EXIST(14, "订单详情不存在"),
    ORDER_STATUS_ERROR(15, "订单状态不正确"),
    UPDATE_ORDER_ERROR(16, "修改订单失败"),
    ORDER_DETAIL_EMPTY(17,"订单详情为空"),
    PAY_STATUS_ERROR(18, "订单支付状态不正确"),
    PARAM_ERROR(19, "参数不正确"),
    CART_EMPTY_ERROR(20,"购物车不能为空"),
    ORDER_OWNER_ERROR(21, "无权访问该订单"),
    WECHAT_MP_ERROR(22, "微信公众账号方面错误"),
    WECHAT_PAY_NOTIFY_ERROR(23, "微信支付异步通知校验错误"),
    ORDER_CANCEL_SUCCESS(24, "订单取消成功"),
    ORDER_FINISH_SUCCESS(25, "卖家完成订单成功"),
    PRODUCT_STATUS_ERROR(26, "商品状态不正确"),
    ON_SALE_SUCCCESS(27, "上架商品成功"),
    DOWN_SALE_SUCCCESS(28, "下架商品成功"),
    PRODUCT_UPDATE_SUCCESS(29, "商品信息修改成功"),
    CATEGORY_INDEX_SUCCESS(30, "类目修改或新增成功");


    private Integer code;
    private String msg;

    ResultStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }}
