package com.su.service.impl;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.su.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    CategoryServiceImpl categoryService;

    @Test
    public void findOneTest(){
        ProductCategory productCategory = categoryService.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    public void findAllTest(){
        List<ProductCategory> list = categoryService.findAll();
        list.forEach(System.out::println);
//        list.forEach(item -> System.out.println(item));
    }

    @Test
    public void findByCategoryTypeInTest(){
        List<ProductCategory> list = categoryService.findByCategoryTypeIn(Arrays.asList(1, 2, 3, 4, 5));
        list.forEach(System.out::println);
    }

    @Test
    public void saveTest(){
        ProductCategory productCategory = categoryService.save(new ProductCategory("男生专享", 7));
        System.out.println(productCategory);
    }

}