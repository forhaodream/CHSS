package com.sheepshop.businessside.entity;

import java.util.HashMap;
import java.util.List;

/**
 * Created by CH
 * on 2019/11/11 09:51
 */
public class CouponListBean {


    private int totalPage;
    private int totalNum;
    private int totalNumLast;

    public int getTotalNumLast() {
        return totalNumLast;
    }

    public void setTotalNumLast(int totalNumLast) {
        this.totalNumLast = totalNumLast;
    }

    private List<ListBean> list;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * couponId : 18
         * couponName : 测试订单123456
         * couponPic : http://image.lnhcsk.com/coupon/20191105/0766da4f02561b2880ef4e1a2e383b10.jpg
         * couponStartTime : 2019-10-07
         * couponEndTime : 2019-11-30
         * couponUseNum :
         * couponExchangeNum :
         * addTime :
         */

        private String couponId;
        private String couponName;
        private String couponPic;
        private String couponStartTime;
        private String couponEndTime;
        private String couponUseNum;
        private String couponExchangeNum;
        private String addTime;
        private String isOpen;
        private String uiHeadurl;
        private boolean isCheck;

        public String getUiHeadurl() {
            return uiHeadurl == null ? "" : uiHeadurl;
        }

        public void setUiHeadurl(String uiHeadurl) {
            this.uiHeadurl = uiHeadurl == null ? "" : uiHeadurl;
        }

        public String getIsOpen() {
            return isOpen == null ? "" : isOpen;
        }

        public void setIsOpen(String isOpen) {
            this.isOpen = isOpen == null ? "" : isOpen;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private HashMap<Integer, Boolean> Maps;


        public HashMap<Integer, Boolean> getMaps() {
            return Maps;
        }

        public void setMaps(HashMap<Integer, Boolean> maps) {
            Maps = maps;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }

        public String getCouponName() {
            return couponName;
        }

        public void setCouponName(String couponName) {
            this.couponName = couponName;
        }

        public String getCouponPic() {
            return couponPic;
        }

        public void setCouponPic(String couponPic) {
            this.couponPic = couponPic;
        }

        public String getCouponStartTime() {
            return couponStartTime;
        }

        public void setCouponStartTime(String couponStartTime) {
            this.couponStartTime = couponStartTime;
        }

        public String getCouponEndTime() {
            return couponEndTime;
        }

        public void setCouponEndTime(String couponEndTime) {
            this.couponEndTime = couponEndTime;
        }

        public String getCouponUseNum() {
            return couponUseNum;
        }

        public void setCouponUseNum(String couponUseNum) {
            this.couponUseNum = couponUseNum;
        }

        public String getCouponExchangeNum() {
            return couponExchangeNum;
        }

        public void setCouponExchangeNum(String couponExchangeNum) {
            this.couponExchangeNum = couponExchangeNum;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }
    }

}
