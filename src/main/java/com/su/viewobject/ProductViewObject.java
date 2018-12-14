package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * 返回给视图时的商品信息（包含类目信息）
 */
@Data
public class ProductViewObject {

    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoViewObject> productInfoViewObjects;
}
