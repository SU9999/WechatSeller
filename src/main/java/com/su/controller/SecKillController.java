package com.su.controller;

import com.su.dto.CartDTO;
import com.su.service.ProductService;
import com.su.service.SecKillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 秒杀的控制器：用于测试并发访问
 */
@RestController
@RequestMapping("/secKill")
public class SecKillController {

    private final String productId = "abc";
    @Autowired
    private SecKillService secKillService;

    @GetMapping("/product")
    public String secKill(){

        secKillService.secKill(productId);
        return secKillService.query(productId);
    }

    @GetMapping("/query")
    public String query(){
        return secKillService.query(productId);
    }
}
