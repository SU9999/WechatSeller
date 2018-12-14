package com.su.enums;

import lombok.Getter;

/**
 *  表示订单状态的枚举类：新下单，已完成，已取消
 */
@Getter
public enum OrderStatusEnum {
    NEW(0, "新下单"),
    FINISHED(1, "已完成"),
    CANCEL(2, "已取消");

    private int code;
    private String msg;

    private OrderStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
