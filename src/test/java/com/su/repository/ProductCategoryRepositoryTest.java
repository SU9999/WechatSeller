package com.su.repository;

import com.su.model.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductCategoryRepositoryTest {

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Test
    public void testFindOne(){
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    public void testSave(){
        ProductCategory category = new ProductCategory("网红套餐", 10);
        productCategoryRepository.save(category);
    }

    /**
     *  更新操作应该先从数据库中取出数据，修改相应的字段后，再次保存数据
     *  如果直接创建一个新的数据，则会导致数据库中其他没有被设置的字段变为null或默认值
     */
    @Test
    public void testUpdate(){
//        ProductCategory category = new ProductCategory("男生最爱", 3);
        ProductCategory category = productCategoryRepository.findOne(2);
        category.setCategoryName("女生最爱套餐");
        productCategoryRepository.save(category);
    }

    @Test
    public void testFindByCategoryTypeIn(){
        List<ProductCategory> list = productCategoryRepository.findByCategoryTypeIn(Arrays.asList(1, 2, 3));
        list.forEach(item -> System.out.println(item));
    }
}