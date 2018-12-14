package com.su.utils;

/**
 *  处理数字（金额）相关的工具类
 */
public class MathUtil {

    private static final Double MONEY_MIN_SIZE = 0.01;
    /**
     *  比较两个双精度浮点数是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1, Double d2){
        if (Math.abs(d1-d2) < MONEY_MIN_SIZE){
            return true;
        } else {
            return false;
        }
    }
}
