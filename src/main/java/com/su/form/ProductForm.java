package com.su.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 用于封装商品新增或修改时的form表单的内容
 */
@Data
public class ProductForm {

    /** 商品Id */
    private String productId;

    /** 商品名称 */
    @NotEmpty(message = "商品名字必填")
    private String productName;

    /** 商品价格 */
    @NotNull(message = "商品价格不能为空")
    @DecimalMin(value = "0", message = "商品价格不能小于0")
    private BigDecimal productPrice;

    /** 商品库存 */
    @NotNull(message = "商品库存必填")
    @Min(value = 0, message = "库存不能小于0")
    private Integer productStock;

    /** 商品描述 */
    @NotEmpty(message = "商品描述信息必填")
    private String productDescription;

    /** 商品图片 */
    @NotEmpty(message = "商品图片url必填")
    private String productIcon;

    /** 商品类目 */
    @NotNull(message = "商品类目必填")
    @Min(value = 0, message = "类目类型必须为正数")
    private Integer categoryType;
}
