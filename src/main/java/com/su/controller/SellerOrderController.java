package com.su.controller;

import com.su.dto.OrderDTO;
import com.su.enums.ResultStatusEnum;
import com.su.exception.SellException;
import com.su.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端前端控制器：使用FreeMarker最视图渲染
 */
@Controller
@RequestMapping("/seller/order")
public class SellerOrderController {

    private Logger log = LoggerFactory.getLogger(SellerOrderController.class);

    @Autowired
    private OrderService orderService;

    /**
     * 卖家端查询订单列表
     * @param page：订单查询第几页，前端从1开，而后台处理时应该从0开始
     * @param size：每页的数据条数
     * @param model：返回给前端的数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> model){
        PageRequest pageRequest = new PageRequest(page-1, size);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        model.put("orderDTOPage", orderDTOPage);
        // 传入该参数给前端，当前端为当前页时，做灰色处理
        model.put("currentPage", page);
        model.put("currentSize", size);
        return new ModelAndView("/order/list", model);
    }

    @GetMapping("/cancel")
    public ModelAndView cancen(@RequestParam("orderId") String orderId,
                               Map<String, Object> model){

        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        } catch (SellException e) {
            log.error("【卖家取消订单】取消订单出错，orderId={}，errorCode={}",orderId, e.getCode());
            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.ORDER_CANCEL_SUCCESS.getMsg());
        model.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }

    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String, Object> model){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
        } catch (SellException e){
            log.error("【卖家查看订单】发生异常，e={}", e);
            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", model);
        }
        model.put("orderDTO", orderDTO);
        return new ModelAndView("order/detail", model);
    }

    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam("orderId") String orderId,
                               Map<String, Object> model){
        OrderDTO orderDTO = null;
        try {
            orderDTO = orderService.findOne(orderId);
            orderService.finish(orderDTO);
        } catch (Exception e) {
            log.error("【卖家完成订单】发生异常，e={}", e);
            model.put("msg", e.getMessage());
            model.put("url", "/sell/seller/order/list");

            return new ModelAndView("common/error", model);
        }

        model.put("msg", ResultStatusEnum.ORDER_FINISH_SUCCESS.getMsg());
        model.put("url", "/sell/seller/order/list");
        return new ModelAndView("common/success");
    }
}
