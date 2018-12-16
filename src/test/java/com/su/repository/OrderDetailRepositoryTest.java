package com.su.repository;

import com.su.model.OrderDetail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OrderDetailRepositoryTest {

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Test
    public void saveTest(){
        OrderDetail orderDetail = new OrderDetail("detail-001", "master-001", "abc",
                "皮蛋瘦肉粥", new BigDecimal(2.5),3,
                "/resource/static/image/hotdog.jpg");
        OrderDetail result = orderDetailRepository.save(orderDetail);
        System.out.println(result);
    }

    @Test
    public void findOneTest(){
        OrderDetail result = orderDetailRepository.findOne("detail-001");
        System.out.println(result);
    }

    @Test
    public void findByOrderIdTest(){
        List<OrderDetail> list = orderDetailRepository.findByOrderId("master-001");
        list.forEach(System.out::println);
    }

}