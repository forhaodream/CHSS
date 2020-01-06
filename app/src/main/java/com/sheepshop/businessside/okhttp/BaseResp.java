package com.sheepshop.businessside.okhttp;

import java.io.Serializable;


public class BaseResp<T> implements Serializable {

    /// <summary>
    /// 响应代码
    /// </summary>
    private String code;

    /// <summary>
    /// 反馈信息
    /// </summary>
    private String msg;

    /// <summary>
    /// 返回信息体
    /// </summary>
    private T data;

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code == null ? "" : code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? "" : msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
