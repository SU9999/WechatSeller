package com.su.viewobject;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 返回给视图时的商品信息（包含类目信息）
 */
@Data
public class ProductViewObject implements Serializable {


    private static final long serialVersionUID = -8020366933884238779L;
    @JsonProperty("name")
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoViewObject> productInfoViewObjects;
}
