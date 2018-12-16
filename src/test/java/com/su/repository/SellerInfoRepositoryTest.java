package com.su.repository;

import com.su.model.SellerInfo;
import com.su.utils.KeyUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SellerInfoRepositoryTest {

    private final String EMAIL = "1401538768@qq.com";
    @Autowired
    private SellerInfoRepository sellerInfoRepository;
    @Test
    public void save(){
        SellerInfo sellerInfo = new SellerInfo();
        sellerInfo.setSellerId(KeyUtil.getUniqueKey());
        sellerInfo.setEmail(EMAIL);
        sellerInfo.setUsername("苏童");
        sellerInfo.setPassword("sutong");

        SellerInfo result = sellerInfoRepository.save(sellerInfo);
        System.out.println(result);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOpenid(){
        SellerInfo result = sellerInfoRepository.findByEmail(EMAIL);
        System.out.println(result);
        Assert.assertNotEquals(null, result);
    }

}