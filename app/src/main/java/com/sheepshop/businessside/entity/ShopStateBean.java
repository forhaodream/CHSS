package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/11/18 16:56
 */
public class ShopStateBean {
    private String state;

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state == null ? "" : state;
    }
}
