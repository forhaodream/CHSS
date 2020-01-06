package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2019/11/11 13:13
 */
public class ExchangeListBean {
    private int totalPage;
    private int exchangeNum;
    private int isBackNum;
    private List<ListBean> list;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getExchangeNum() {
        return exchangeNum;
    }

    public void setExchangeNum(int exchangeNum) {
        this.exchangeNum = exchangeNum;
    }

    public int getIsBackNum() {
        return isBackNum;
    }

    public void setIsBackNum(int isBackNum) {
        this.isBackNum = isBackNum;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * dateTime : 2019-10-28
         * couponNum : 1
         * couponUserList : [{"couponId":"4","couponName":"生效代金券1","couponPic":"http://image.lnhcsk.com/1/20191028/53209d6a9e2647e6990b93699fa22442.png","couponExchangeTime":"2019-10-28 10:25:29","uiId":"1002004","uiNickname":"哎呦喂呵","uiHeadurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/icRYJaYFpfdK7OdTcgsXfNNm0ZqKs6vn7ANDWUbDNpicPE7KJrbFv2PQkb9aRQdUPGFYFu4lkyxZKQ30ouIBhUeg/132","couponUserId":"10001","backTime":"2019-11-04 11:47:36","couponStartTime":"2019-10-01","couponEndTime":"2019-11-16"}]
         * isBack : 1
         * businessId : 1
         */

        private String dateTime;
        private String couponNum;
        private String isBack;
        private String businessId;
        private List<CouponUserListBean> couponUserList;

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }

        public String getCouponNum() {
            return couponNum;
        }

        public void setCouponNum(String couponNum) {
            this.couponNum = couponNum;
        }

        public String getIsBack() {
            return isBack;
        }

        public void setIsBack(String isBack) {
            this.isBack = isBack;
        }

        public String getBusinessId() {
            return businessId;
        }

        public void setBusinessId(String businessId) {
            this.businessId = businessId;
        }

        public List<CouponUserListBean> getCouponUserList() {
            return couponUserList;
        }

        public void setCouponUserList(List<CouponUserListBean> couponUserList) {
            this.couponUserList = couponUserList;
        }

        public static class CouponUserListBean {
            /**
             * couponId : 4
             * couponName : 生效代金券1
             * couponPic : http://image.lnhcsk.com/1/20191028/53209d6a9e2647e6990b93699fa22442.png
             * couponExchangeTime : 2019-10-28 10:25:29
             * uiId : 1002004
             * uiNickname : 哎呦喂呵
             * uiHeadurl : http://thirdwx.qlogo.cn/mmopen/vi_32/icRYJaYFpfdK7OdTcgsXfNNm0ZqKs6vn7ANDWUbDNpicPE7KJrbFv2PQkb9aRQdUPGFYFu4lkyxZKQ30ouIBhUeg/132
             * couponUserId : 10001
             * backTime : 2019-11-04 11:47:36
             * couponStartTime : 2019-10-01
             * couponEndTime : 2019-11-16
             */

            private String couponId;
            private String couponName;
            private String couponPic;
            private String couponExchangeTime;
            private String uiId;
            private String uiNickname;
            private String uiHeadurl;
            private String couponUserId;
            private String backTime;
            private String couponStartTime;
            private String couponEndTime;

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

            public String getCouponExchangeTime() {
                return couponExchangeTime;
            }

            public void setCouponExchangeTime(String couponExchangeTime) {
                this.couponExchangeTime = couponExchangeTime;
            }

            public String getUiId() {
                return uiId;
            }

            public void setUiId(String uiId) {
                this.uiId = uiId;
            }

            public String getUiNickname() {
                return uiNickname;
            }

            public void setUiNickname(String uiNickname) {
                this.uiNickname = uiNickname;
            }

            public String getUiHeadurl() {
                return uiHeadurl;
            }

            public void setUiHeadurl(String uiHeadurl) {
                this.uiHeadurl = uiHeadurl;
            }

            public String getCouponUserId() {
                return couponUserId;
            }

            public void setCouponUserId(String couponUserId) {
                this.couponUserId = couponUserId;
            }

            public String getBackTime() {
                return backTime;
            }

            public void setBackTime(String backTime) {
                this.backTime = backTime;
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
        }
    }

}
