package com.su.handler;

import com.su.exception.SellException;
import com.su.utils.ResultViewObjectUtil;
import com.su.viewobject.ResultViewObject;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
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

    /**
     *  处理程序运行中抛出的SellException异常
     */
    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultViewObject handlerSellException(SellException e){
        return ResultViewObjectUtil.error(e.getCode(), e.getMessage());
    }
}
