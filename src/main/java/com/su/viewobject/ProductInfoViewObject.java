package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *  封装返回给视图页面时所需要的商品详细信息
 * @JsonProperty 注解用于指定该属性在转换成json数据时的key
 */
@Data
public class ProductInfoViewObject {

    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    @JsonProperty("icon")
    private String productIcon;
}
