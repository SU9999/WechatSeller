package com.su.exception;

import com.su.enums.ResultStatusEnum;
import lombok.Getter;

@Getter
public class SellException extends RuntimeException {

    private int code;

    public SellException(ResultStatusEnum resultStatusEnum){
        super(resultStatusEnum.getMsg());
        this.code = resultStatusEnum.getCode();
    }

    public SellException(int code, String message){
        super(message);
        this.code = code;
    }
}
