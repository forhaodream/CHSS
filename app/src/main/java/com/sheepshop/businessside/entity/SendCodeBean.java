package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/11/8 20:32
 */
public class SendCodeBean {

    /**
     * msg : 发送成功
     * code : 0
     * data : 0669
     */

    private String msg;
    private String code;
    private String data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
