package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/12/31 14:26
 */
public class GoodsLabelBean {

    private String id;
    private String name;
    private String wordColorCode;
    private String bgColorCode;
    private boolean isClick;

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }

    public String getId() {
        return id == null ? "" : id;
    }

    public void setId(String id) {
        this.id = id == null ? "" : id;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name == null ? "" : name;
    }

    public String getWordColorCode() {
        return wordColorCode == null ? "" : wordColorCode;
    }

    public void setWordColorCode(String wordColorCode) {
        this.wordColorCode = wordColorCode == null ? "" : wordColorCode;
    }

    public String getBgColorCode() {
        return bgColorCode == null ? "" : bgColorCode;
    }

    public void setBgColorCode(String bgColorCode) {
        this.bgColorCode = bgColorCode == null ? "" : bgColorCode;
    }
}
