package com.su.enums;

import lombok.Getter;

/**
 *  支付状态：未支付，已支付
 */
@Getter
public enum  PayStatusEnum implements CodeEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "已支付");

    private Integer code;
    private String msg;

    private PayStatusEnum(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
