package com.su.viewobject;

import lombok.Data;

/**
 * 封装返回的结果，最终作为返回给视图页面的对象
 */
@Data
public class ResultViewObject<T> {
    /** 状态码 */
    private int code;
    /** 提示信息 */
    private String msg;

    /** 实际的返回结果数据：用泛型表示 */
    private T data;
}
