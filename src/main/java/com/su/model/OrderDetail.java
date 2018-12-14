package com.su.model;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 *  订单详情表：主要包含订单的详情，例如，买的哪些东西，等待
 *  订单详情表和订单主笔之间是多对一的关系
 */
@Entity
@Data
@DynamicUpdate
public class OrderDetail {

    @Id
    private String detailId;

    /** 订单id */
    private String orderId;

    /** 商品id */
    private String productId;

    /** 商品名字 */
    private String productName;

    /** 商品单价 */
    private BigDecimal productPrice;

    /** 商品数量 */
    private Integer productQuantity;

    /** 商品小图：url */
    private String productIcon;

    /** 创建订单和修改订单的时间：由数据库创建 */
    private Date createTime;
    private Date updateTime;

    public OrderDetail() {
    }

    public OrderDetail(String detailId,
                       String orderId,
                       String productId,
                       String productName,
                       BigDecimal productPrice,
                       Integer productQuantity,
                       String productIcon) {
        this.detailId = detailId;
        this.orderId = orderId;
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productQuantity = productQuantity;
        this.productIcon = productIcon;
    }
}
