package com.su.service.impl;

import com.su.dto.CartDTO;
import com.su.enums.ProductStatusEnum;
import com.su.model.ProductInfo;
import com.su.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl service;

    @Test
    public void findOne() {
        ProductInfo result = service.findOne("123456");
//        System.out.println(result);
        Assert.assertEquals("123456", result.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> list = service.findUpAll();
        list.forEach(System.out::println);
        assertNotEquals(null, list);
    }

    @Test
    public void findAll() {
        PageRequest page = new PageRequest(1, 2);
        Page<ProductInfo> result = service.findAll(page);
        System.out.println(result.getTotalElements());
        System.out.println(result.getTotalPages());
        System.out.println("=========page的内容-=============");
        List<ProductInfo> list = result.getContent();
        list.forEach(System.out::println);
    }

    @Test
    public void save() {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("111111");
        productInfo.setCategoryType(7);
        productInfo.setProductDescription("纯天然，无公害大白菜");
        productInfo.setProductIcon("/resource/static/image/大白菜.jpg");
        productInfo.setProductName("糖醋白菜");
        productInfo.setProductPrice(new BigDecimal(1.5));
        productInfo.setProductStock(99);
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());

        ProductInfo result = service.save(productInfo);
        System.out.println(result);
    }

    @Test
    public void subStackTest(){
        List<CartDTO> cartDTOList = Arrays.asList(new CartDTO("abc", 100));
        service.subStock(cartDTOList);
    }
    @Test
    public void addStackTest(){
        List<CartDTO> cartDTOList = Arrays.asList(new CartDTO("abc", 100));
        service.addStock(cartDTOList);
    }
    
    @Test
    public void onSaleTest(){
        ProductInfo productInfo = service.onSale("123456");

        System.out.println(productInfo);
        Assert.assertEquals(ProductStatusEnum.UP.getCode(), productInfo.getProductStatus());
    }

    @Test
    public void offSaleTest(){
        ProductInfo productInfo = service.offSale("123456");

        System.out.println(productInfo);
        Assert.assertEquals(ProductStatusEnum.DOWN.getCode(), productInfo.getProductStatus());
    }
}