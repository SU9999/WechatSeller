package com.su.controller;

import com.su.converter.OrderForm2OrderDTOConverter;
import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.form.OrderForm;
import com.su.service.BuyerService;
import com.su.service.impl.OrderServiceImpl;
import com.su.utils.ResultViewObjectUtil;
import com.su.viewobject.ResultViewObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
public class BuyerOrderController {

    private Logger log = LoggerFactory.getLogger(BuyerOrderController.class);

    @Autowired
    OrderServiceImpl orderService;

    @Autowired
    BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultViewObject<Map<String, String>> createOrder(@Valid OrderForm orderForm,
                                                             BindingResult bindingResult){
        // 当绑定的参数中有错误数据时，抛出异常信息
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确：orderForm={}", orderForm);
            // 将绑定参数的对象中的错误信息作为message传递给异常SellException
            throw new SellException(ResultStatusEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        // 创建订单：第一步：将orderForm对象装换成OrderDTO对象
        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】购物车不能为空，orderDTO={}", orderDTO);
            throw new SellException(ResultStatusEnum.CART_EMPTY_ERROR);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        // 构造结果集对象
        Map<String, String> map = new HashMap<>();
        map.put("orderId", createResult.getOrderId());
        return ResultViewObjectUtil.success(map);
    }
    // 查看订单
    @GetMapping("/list")
    public ResultViewObject listOrder(@RequestParam("openid") String openid,
                                      @RequestParam(value = "page", defaultValue = "0") Integer page,
                                      @RequestParam(value = "size", defaultValue = "10") Integer size){
        // 对数据参数进行校验
        if (StringUtils.isEmpty(openid)){
            log.error("【查看订单】微信openid为空");
            throw new SellException(ResultStatusEnum.PARAM_ERROR);
        }

        // 创建一个page对象
        PageRequest pageRequest = new PageRequest(page, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(openid, pageRequest);

        List<OrderDTO> orderDTOList = orderDTOPage.getContent();
        return ResultViewObjectUtil.success(orderDTOList);
    }
    // 查看订单详情
    @GetMapping("/detail")
    public ResultViewObject<OrderDTO> detail(@RequestParam("openid") String openid,
                                             @RequestParam("orderId") String orderId){
        // 对数据参数进行校验
        if (StringUtils.isEmpty(openid)){
            log.error("【查看订单】微信openid为空");
            throw new SellException(ResultStatusEnum.PARAM_ERROR);
        }

        // 需要做安全性校验: 使用BuyerService对象做校验逻辑处理
        OrderDTO orderDTO = buyerService.findOrderOne(openid, orderId);

        return ResultViewObjectUtil.success(orderDTO);
    }
    // 取消订单
    @PostMapping("/cancel")
    public ResultViewObject cancel(@RequestParam("openid") String openid,
                                   @RequestParam("orderId") String orderId){
        // 此处需要做安全性校验,使用BuyerService对象做校验逻辑处理
        buyerService.cancelOrder(openid, orderId);

        return ResultViewObjectUtil.success();
    }
    // 支付订单
}
