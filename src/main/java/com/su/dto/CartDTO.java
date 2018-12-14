package com.su.dto;

import lombok.Data;

/**
 *  购物车: 只保存前端传入的两个字段：商品id和商品数量
 */
@Data
public class CartDTO {
    private String productId;

    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
