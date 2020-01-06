package com.sheepshop.businessside.entity;

/**
 * Demo class
 *
 * @author Administrator
 * @date 2019/11/4
 * @decs：
 */
public class DicEntity {


    /**
     * cuisineSort : 0
     * cuisineId : 1
     * cuisineName : 川菜
     */

    private int cuisineSort;
    private int cuisineId;
    private String cuisineName;

    public int getCuisineSort() {
        return cuisineSort;
    }

    public void setCuisineSort(int cuisineSort) {
        this.cuisineSort = cuisineSort;
    }

    public int getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(int cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getCuisineName() {
        return cuisineName;
    }

    public void setCuisineName(String cuisineName) {
        this.cuisineName = cuisineName;
    }

}
