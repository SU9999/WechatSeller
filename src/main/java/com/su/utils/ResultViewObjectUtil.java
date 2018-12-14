package com.su.utils;

import com.su.viewobject.ResultViewObject;

/**
 * 对返回的结果对象进行分装
 */
public class ResultViewObjectUtil {

    /**
     * 当返回成功结果时，调用该方法进行封装
     * @return
     */
    public static ResultViewObject success(Object data){
        ResultViewObject result = new ResultViewObject();
        result.setCode(0);
        result.setMsg("成功");
        result.setData(data);

        return result;
    }

    /**
     * 返回成功，但不向前端发送数据
     */
    public static ResultViewObject success(){
        return success(null);
    }

    /**
     * 当发生错误时，封装错误代码code和错误信息msg
     */
    public static ResultViewObject error(int code, String msg){
        ResultViewObject result = new ResultViewObject();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);

        return result;
    }
}
