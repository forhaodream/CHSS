package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/12/13 13:57
 */
public class CenterTabBean {
    private String title;
    private int img;

    public CenterTabBean(String title, int img) {
        this.title = title;
        this.img = img;
    }

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title == null ? "" : title;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }
}
