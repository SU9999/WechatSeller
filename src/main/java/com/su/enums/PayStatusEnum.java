package com.su.enums;

import lombok.Getter;

/**
 *  支付状态：未支付，已支付
 */
@Getter
public enum  PayStatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "已支付");

    private int code;
    private String msg;

    private PayStatusEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }
}
