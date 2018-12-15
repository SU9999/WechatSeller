package com.su.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *  处理SellException异常信息
 */
@ControllerAdvice
public class SellExceptionHandler {

    /**
     * 处理登录校验时发生的异常
     * @return
     */
    @ExceptionHandler(value = com.su.exception.SellerAuthorizeException.class)
    public ModelAndView handlerSellerAuthorizeException(){
        // 登录校验发生异常时，则跳转到登录页面
        return new ModelAndView("seller/login");
    }
}
