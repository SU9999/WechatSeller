package com.su.model.mapper;

import com.su.model.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryMapperTest {

    @Autowired
    ProductCategoryMapper mapper;

    @Test
    public void insertTest(){
        Map<String, Object> map = new HashMap<>();
        map.put("categoryName", "苏童专用");
        map.put("categoryType", 101);
        int result = mapper.insert(map);
        System.out.println(result);
        Assert.assertEquals(1, result);
    }

    @Test
    public void findByTypeTest(){
        ProductCategory productCategory = mapper.findByType(101);
        System.out.println(productCategory);
        Assert.assertNotNull(productCategory);
    }
}