package com.su.repository;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.su.model.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;
    
    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setCategoryType(7);
        productInfo.setProductDescription("新鲜的小米粥，纯粗粮");
        productInfo.setProductIcon("/resource/static/image/小米粥.jpg");
        productInfo.setProductName("小米粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(88);
        productInfo.setProductStatus(0);

        ProductInfo result = productInfoRepository.save(productInfo);
        System.out.println(result);
    }
    
    @Test
    public void findOneTest(){
        ProductInfo result = productInfoRepository.findOne("abc");
        System.out.println(result);
    }

    @Test
    public void testUpdate(){
        ProductInfo result = productInfoRepository.findOne("abc");
        result.setProductName("皮蛋瘦肉粥");
        ProductInfo info = productInfoRepository.save(result);
        System.out.println(info);
    }

    @Test
    public void findByProductStatusTest(){
        List<ProductInfo> list = productInfoRepository.findByProductStatus(0);
        list.forEach(System.out::println);
    }
}