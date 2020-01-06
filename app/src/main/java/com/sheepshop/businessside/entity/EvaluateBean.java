package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2019/11/16 10:58
 */
public class EvaluateBean {

    private int endId;
    private int isEnd;
    private List<ListBean> list;

    public int getEndId() {
        return endId;
    }

    public void setEndId(int endId) {
        this.endId = endId;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * bucId : 13
         * bucCouponUserId : 10026
         * bucStar : 10
         * bucText : 234234324923842094890289048239048902384903289408239048902384023804809234
         * bucPic : http://testimage.lnhcsk.com/comment/coupon/20191112/552c65fe8c8e4cd0b579efe87870b769.jpeg
         * bucTime : 2019-11-12 19:28:02
         * bucBusinessId : 1
         * bucUserId : 1002140
         * bucCouponModelId : 4
         * bucCouponName : 生效代金券1
         * bucTagId : 1
         * bucLabelList : ["环境优美"]
         * bucNickname : Fán
         * bucHeadurl : http://sheepshop.lnhcsk.com/img/avatar.png
         */

        private int bucId;
        private String bucCouponUserId;
        private int bucStar;
        private String bucText;
        private String bucPic;
        private String bucTime;
        private int bucBusinessId;
        private int bucUserId;
        private int bucCouponModelId;
        private String bucCouponName;
        private String bucTagId;
        private String bucNickname;
        private String bucHeadurl;
        private List<String> bucLabelList;

        public int getBucId() {
            return bucId;
        }

        public void setBucId(int bucId) {
            this.bucId = bucId;
        }

        public String getBucCouponUserId() {
            return bucCouponUserId;
        }

        public void setBucCouponUserId(String bucCouponUserId) {
            this.bucCouponUserId = bucCouponUserId;
        }

        public int getBucStar() {
            return bucStar;
        }

        public void setBucStar(int bucStar) {
            this.bucStar = bucStar;
        }

        public String getBucText() {
            return bucText;
        }

        public void setBucText(String bucText) {
            this.bucText = bucText;
        }

        public String getBucPic() {
            return bucPic;
        }

        public void setBucPic(String bucPic) {
            this.bucPic = bucPic;
        }

        public String getBucTime() {
            return bucTime;
        }

        public void setBucTime(String bucTime) {
            this.bucTime = bucTime;
        }

        public int getBucBusinessId() {
            return bucBusinessId;
        }

        public void setBucBusinessId(int bucBusinessId) {
            this.bucBusinessId = bucBusinessId;
        }

        public int getBucUserId() {
            return bucUserId;
        }

        public void setBucUserId(int bucUserId) {
            this.bucUserId = bucUserId;
        }

        public int getBucCouponModelId() {
            return bucCouponModelId;
        }

        public void setBucCouponModelId(int bucCouponModelId) {
            this.bucCouponModelId = bucCouponModelId;
        }

        public String getBucCouponName() {
            return bucCouponName;
        }

        public void setBucCouponName(String bucCouponName) {
            this.bucCouponName = bucCouponName;
        }

        public String getBucTagId() {
            return bucTagId;
        }

        public void setBucTagId(String bucTagId) {
            this.bucTagId = bucTagId;
        }

        public String getBucNickname() {
            return bucNickname == null ? "无名氏" : bucNickname;
        }

        public void setBucNickname(String bucNickname) {
            this.bucNickname = bucNickname;
        }

        public String getBucHeadurl() {
            return bucHeadurl;
        }

        public void setBucHeadurl(String bucHeadurl) {
            this.bucHeadurl = bucHeadurl;
        }

        public List<String> getBucLabelList() {
            return bucLabelList;
        }

        public void setBucLabelList(List<String> bucLabelList) {
            this.bucLabelList = bucLabelList;
        }
    }
}
