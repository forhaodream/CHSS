package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/12/25 11:45
 */
public class CenterStateBean {
    private String state;

    public String getState() {
        return state == null ? "" : state;
    }

    public void setState(String state) {
        this.state = state == null ? "" : state;
    }
}
