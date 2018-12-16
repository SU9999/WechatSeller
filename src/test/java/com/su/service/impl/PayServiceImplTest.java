package com.su.service.impl;

import com.su.dto.OrderDTO;
import com.su.service.OrderService;
import com.su.service.PayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PayServiceImplTest {

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("master-001");
        payService.create(orderDTO);
    }

    @Test
    public void reFund(){
        OrderDTO orderDTO = orderService.findOne("master-001");
        payService.reFund(orderDTO);
    }
}