package com.sheepshop.businessside.entity;

/**
 * Demo class
 *
 * @author Administrator
 * @date 2019/11/4
 * @decs：
 */
public class AreaSelectBean {


    /**
     * id : 33013
     * areaCode : 130202
     * parentId : 32521
     * level : 3
     * name : 路南区
     */

    private int id;
    private int areaCode;
    private int parentId;
    private int level;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(int areaCode) {
        this.areaCode = areaCode;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
