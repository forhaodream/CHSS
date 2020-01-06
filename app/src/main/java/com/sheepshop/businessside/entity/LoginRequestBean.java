package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/11/8 19:43
 */
public class LoginRequestBean {
    private String mobile;
    private String code;

    public String getMobile() {
        return mobile == null ? "" : mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? "" : mobile;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code == null ? "" : code;
    }
}
