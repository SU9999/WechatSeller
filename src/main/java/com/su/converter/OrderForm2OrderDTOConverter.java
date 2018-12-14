package com.su.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.form.OrderForm;
import com.su.model.OrderDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class OrderForm2OrderDTOConverter {

    private static Logger log = LoggerFactory.getLogger(OrderForm2OrderDTOConverter.class);

    public static OrderDTO convert(OrderForm orderForm){
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setBuyerAddress(orderForm.getAddress());


        // 将orderForm中的items字段转换成OrderDetail的list集合
        // 使用Gson做json格式转换
        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(),
                    new TypeToken<List<OrderDetail>>() {
                    }.getType());
        } catch (Exception e){
            log.error("【对象转换】json格式错误：string={}", orderForm.getItems());
            throw new SellException(ResultStatusEnum.PARAM_ERROR);
        }

        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
