package com.su.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import com.su.model.OrderDetail;
import com.su.utils.EnumUtil;
import com.su.utils.serializer.Date2LongSerializer;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *  数据传输对象：用于在Controller，Service，Model之间进行数据传输
 *  @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL) : 表示当对象为null时，不对对象进行json封装操作（已过时）
 *  @JsonInclude(JsonInclude.Include.NON_NULL): 用法同上，新的用法，替换了上面的用法
 */
@Data
//@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    /** 订单id */
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
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    /**
     * 当字段必须返回，而且不能返回null时，可以通过附初始值的方式，返回一个空的list"[]"或空字符串""
     */
//    private List<OrderDetail> orderDetailList = new ArrayList<>();
    private List<OrderDetail> orderDetailList;

    /**
     *  增加两个通过枚举的code值获取枚举对象的方法，
     *  用于处理支付状态和订单状态时，将code值转化为对象描述信息
     * @JsonIgnore 注解：当把该对象转化成json格式时，会忽略被该注解标注的字段，否则会造成字段冗余
     */
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getEnumByCode(payStatus, PayStatusEnum.class);
    }
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getEnumByCode(orderStatus, OrderStatusEnum.class);
    }
}
