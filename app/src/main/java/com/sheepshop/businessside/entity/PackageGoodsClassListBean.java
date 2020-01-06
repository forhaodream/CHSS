package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/12/25 16:09
 */
public class PackageGoodsClassListBean {
    /**
     * id : 1
     * name : 荤菜类
     */

    private int id;
    private String name;

    public String getNum() {
        return num == null ? "" : num;
    }

    public void setNum(String num) {
        this.num = num == null ? "" : num;
    }

    private String num;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
