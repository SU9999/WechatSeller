package com.su.repository;

import com.su.enums.OrderStatusEnum;
import com.su.enums.PayStatusEnum;
import com.su.model.OrderMaster;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository orderMasterRepository;
    /** 测试保存和插入数据 */
    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster("master-001", "苏童", "18890807009",
                "重庆邮电大学", "sutong995", new BigDecimal(188),
                OrderStatusEnum.NEW.getCode(), PayStatusEnum.WAIT.getCode());
        OrderMaster result = orderMasterRepository.save(orderMaster);
        System.out.println(result);
    }

    @Test
    public void findOneTest(){
        OrderMaster result = orderMasterRepository.findOne("master-001");
        System.out.println(result);
    }

    @Test
    public void findByBuyerOpenidTest(){
        PageRequest pageRequest = new PageRequest(0, 2);
        Page<OrderMaster> result = orderMasterRepository.findByBuyerOpenid("sutong99", pageRequest);

        System.out.println("总页数: " + result.getTotalPages());
        System.out.println("总数据条数: " + result.getTotalElements());
        List<OrderMaster> list = result.getContent();
        list.forEach(System.out::println);
    }
}