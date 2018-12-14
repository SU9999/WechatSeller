package com.su.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *  封装表单数据：主要用于封装前端的form表单中的信息
 */
@Data
public class OrderForm {

    /** 买家姓名 */
    @NotEmpty(message = "姓名必填")
    private String name;

    /** 买家手机号 */
    @NotEmpty(message = "手机号必填")
    private String phone;

    /** 买家地址 */
    @NotEmpty(message = "地址必填")
    private String address;

    /** 买家微信openid */
    @NotEmpty(message = "微信openid必填")
    private String openid;

    /**
     * 封装买家购物车信息
     */
    @NotEmpty(message = "购物车必填")
    private String items;
}
