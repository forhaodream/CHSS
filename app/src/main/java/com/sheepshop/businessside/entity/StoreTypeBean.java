package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2019/11/27 14:13
 */
public class StoreTypeBean {


    /**
     * msg : 常用语标签
     * code : 0
     * data : [{"labelId":1,"labelSort":0,"labelDetail":"无需预约，消费高峰期可能需要等位，商家提供免费WiFi"},{"labelId":2,"labelSort":1,"labelDetail":"停车位收费标准：咨询商家，发票问题请询问商家，团购用户不可同时享受商家其他优惠"}]
     */

    private String msg;
    private String code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
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
}
