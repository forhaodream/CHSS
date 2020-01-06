package com.sheepshop.businessside.ui.bean;

/**
 * Created by CH
 * on 2020/1/3 11:14
 */
public class SaveDataBean {
    private String key;
    private String value;

    public String getKey() {
        return key == null ? "" : key;
    }

    public void setKey(String key) {
        this.key = key == null ? "" : key;
    }

    public String getValue() {
        return value == null ? "" : value;
    }

    public void setValue(String value) {
        this.value = value == null ? "" : value;
    }
}
