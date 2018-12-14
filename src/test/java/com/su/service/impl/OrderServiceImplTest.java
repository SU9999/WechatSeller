package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.model.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderServiceImpl orderService;

    private final String buyerOpenid = "sutong995";

    @Test
    public void createOrder() {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerAddress("湖北经济学院");
        orderDTO.setBuyerName("张三");
        orderDTO.setBuyerOpenid(buyerOpenid);
        orderDTO.setBuyerPhone("18890806996");

        List<OrderDetail> orderDetailList = new ArrayList<>();
        OrderDetail orderDetailOne = new OrderDetail();
        orderDetailOne.setProductId("111111");
        orderDetailOne.setProductQuantity(10);
        orderDetailList.add(orderDetailOne);

        OrderDetail orderDetail2 = new OrderDetail();
        orderDetail2.setProductId("abc");
        orderDetail2.setProductQuantity(10);
        orderDetailList.add(orderDetail2);
        orderDTO.setOrderDetailList(orderDetailList);

        System.out.println(orderDTO);

        OrderDTO result = orderService.create(orderDTO);
        Assert.assertNotNull(result);
    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne("f3cca371-73e3-40");
        System.out.println(orderDTO);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest pageRequest = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(buyerOpenid, pageRequest);

        System.out.println("总页数： " + orderDTOPage.getTotalPages());
        System.out.println("总元素： " + orderDTOPage.getTotalElements());
        orderDTOPage.getContent().forEach(System.out::println);
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne("f3cca371-73e3-40");
        OrderDTO updateOrderDTO = orderService.cancel(orderDTO);
        System.out.println(updateOrderDTO);

        Assert.assertNotEquals(orderDTO.getOrderStatus(), updateOrderDTO.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne("f3cca371-73e3-40");
        OrderDTO updateOrderDTO = orderService.finish(orderDTO);
        System.out.println(updateOrderDTO);

        Assert.assertNotEquals(orderDTO.getOrderStatus(), updateOrderDTO.getOrderStatus());
    }

    @Test
    public void pay() {
        OrderDTO orderDTO = orderService.findOne("f3cca371-73e3-40");

        OrderDTO updateOrderDTO = orderService.pay(orderDTO);
        System.out.println(updateOrderDTO);

        Assert.assertNotEquals(orderDTO.getPayStatus(), updateOrderDTO.getPayStatus());
    }

    @Test
    public void findList2(){
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderDTO> orderDTOPage = orderService.findList(pageRequest);

        System.out.println("总页数：" + orderDTOPage.getTotalPages());
        System.out.println("总记录数：" + orderDTOPage.getTotalElements());
        System.out.println(orderDTOPage.getContent());
        Assert.assertNotEquals(0, orderDTOPage.getTotalElements());
    }
}