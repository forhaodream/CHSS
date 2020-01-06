package com.sheepshop.businessside.entity;

/**
 * Demo class
 *
 * @author Administrator
 * @date 2019/11/4
 * @decs：
 */
public class QuickinputEntity {


    /**
     * labelId : 1
     * labelSort : 0
     * labelDetail : 无需预约，消费高峰期可能需要等位，商家提供免费WiFi
     */

    private int labelId;
    private int labelSort;
    private String labelDetail;

    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    public int getLabelSort() {
        return labelSort;
    }

    public void setLabelSort(int labelSort) {
        this.labelSort = labelSort;
    }

    public String getLabelDetail() {
        return labelDetail;
    }

    public void setLabelDetail(String labelDetail) {
        this.labelDetail = labelDetail;
    }
}
