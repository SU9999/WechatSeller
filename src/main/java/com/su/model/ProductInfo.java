package com.su.model;

import com.su.enums.ProductStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品类
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo {

    @Id
    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;
    /** 描述信息 */
    private String productDescription;
    /** 商品小图：url地址 */
    private String productIcon;
    /** 类目编号*/
    private Integer categoryType;

    /** 商品的状态信息：默认0表示刚上架 */
    private Integer productStatus = ProductStatusEnum.UP.getCode();

    /** 创建时间和更新时间：由数据库自动创建和更新 */
    private Date createTime;
    private Date updateTime;

    public ProductInfo() {
    }

    public ProductInfo(String productId,
                       String productName,
                       BigDecimal productPrice,
                       Integer productStock,
                       String productDescription,
                       String productIcon,
                       Integer categoryType) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.categoryType = categoryType;
    }
}
