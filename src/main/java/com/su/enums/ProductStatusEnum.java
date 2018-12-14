package com.su.enums;

import lombok.Getter;

/**
 *  使用枚举表示商品的状态信息
 *  0：表示上架商品
 *  1：表示已下架商品
 */
@Getter
public enum ProductStatusEnum {

    UP(0, "上架商品"), DOWN(1, "已下架商品");

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
