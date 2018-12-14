package com.su.utils;

import com.su.enums.CodeEnum;

/**
 * 用于处理枚举
 */
public class EnumUtil {

    // 处理枚举，根据枚举类的code值，返回该枚举对象
    public static <T extends CodeEnum> T getEnumByCode(Integer code, Class<T> enumClass){
        for (T item: enumClass.getEnumConstants()){
            if (item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }
}
