package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2019/11/9 16:36
 */
public class ShopFirstInfoBean {
    private int bdStatus;
    private int bdId;
    private int couponUserNumNow;
    private String bdStar;
    private String bdScore;
    private String bdAddress;
    private String bdName;
    private String bdLogo;
    private int couponUserNumAll;
    private List<CommentListBean> commentList;

    public int getBdStatus() {
        return bdStatus;
    }

    public void setBdStatus(int bdStatus) {
        this.bdStatus = bdStatus;
    }

    public int getBdId() {
        return bdId;
    }

    public void setBdId(int bdId) {
        this.bdId = bdId;
    }

    public int getCouponUserNumNow() {
        return couponUserNumNow;
    }

    public void setCouponUserNumNow(int couponUserNumNow) {
        this.couponUserNumNow = couponUserNumNow;
    }

    public String getBdStar() {
        return bdStar;
    }

    public void setBdStar(String bdStar) {
        this.bdStar = bdStar;
    }

    public String getBdScore() {
        return bdScore;
    }

    public void setBdScore(String bdScore) {
        this.bdScore = bdScore;
    }

    public String getBdAddress() {
        return bdAddress;
    }

    public void setBdAddress(String bdAddress) {
        this.bdAddress = bdAddress;
    }

    public String getBdName() {
        return bdName;
    }

    public void setBdName(String bdName) {
        this.bdName = bdName;
    }

    public String getBdLogo() {
        return bdLogo;
    }

    public void setBdLogo(String bdLogo) {
        this.bdLogo = bdLogo;
    }

    public int getCouponUserNumAll() {
        return couponUserNumAll;
    }

    public void setCouponUserNumAll(int couponUserNumAll) {
        this.couponUserNumAll = couponUserNumAll;
    }

    public List<CommentListBean> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentListBean> commentList) {
        this.commentList = commentList;
    }

    public static class CommentListBean {
        /**
         * bucId : 6
         * bucCouponUserId : 10001
         * bucStar : 10
         * bucText : 主人公尝试着去用某种方式渐渐的很潇洒地释****怀那些自己经历的伤感
         * bucPic :
         * bucTime : 2019-11-08 09:48:34
         * bucBusinessId : 1
         * bucUserId : 1002004
         * bucCouponModelId : 4
         * bucCouponName : 生效代金券1
         * bucTagId : 1,3
         * bucLabelList : ["环境优美","服务良好"]
         * bucNickname : 哎呦喂呵
         * bucHeadurl : http://thirdwx.qlogo.cn/mmopen/vi_32/icRYJaYFpfdK7OdTcgsXfNNm0ZqKs6vn7ANDWUbDNpicPE7KJrbFv2PQkb9aRQdUPGFYFu4lkyxZKQ30ouIBhUeg/132
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

