package com.su.logger;

import lombok.Data;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class LoggerTest {
    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void testLogger(){
        String name = "Tom";
        String password = "haha";

        logger.debug("debug.....");
        logger.info("info....");
        // 使用{}占位符的方式，输出变量
        logger.info("username: {}; password: {}", name, password);
        logger.error("error....");

        logger.warn("warn....");
    }

    public void test(){

    }

}
