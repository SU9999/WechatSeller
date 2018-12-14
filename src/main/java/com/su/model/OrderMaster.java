package com.su.model;

import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单主表：包含每一笔订单的信息，包括订单总额，买家名称电话等
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {

    /** 订单id */
    @Id
    private String orderId;

    /** 买家名字 */
    private String buyerName;
    /** 买家电话 */
    private String buyerPhone;
    /** 买家地址 */
    private String buyerAddress;
    /** 买家微信id */
    private String buyerOpenid;

    /** 订单总金额 */
    private BigDecimal orderAmount;
    /** 订单状态, 默认为0，新下单 */
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /** 支付状态，默认为0，表示等待支付 */
    private Integer payStatus = PayStatusEnum.WAIT.getCode();

    /** 订单创建时间以及修改时间：由数据库自动创建于更新 */
    private Date createTime;
    private Date updateTime;

    public OrderMaster() {
    }

    /**
     * @param orderId
     * @param buyerName
     * @param buyerPhone
     * @param buyerAddress
     * @param buyerOpenid
     * @param orderAmount
     * @param orderStatus
     * @param payStatus
     */
    public OrderMaster(String orderId,
                       String buyerName,
                       String buyerPhone,
                       String buyerAddress,
                       String buyerOpenid,
                       BigDecimal orderAmount,
                       Integer orderStatus,
                       Integer payStatus) {
        this.orderId = orderId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerAddress = buyerAddress;
        this.buyerOpenid = buyerOpenid;
        this.orderAmount = orderAmount;
        this.orderStatus = orderStatus;
        this.payStatus = payStatus;
    }
}
