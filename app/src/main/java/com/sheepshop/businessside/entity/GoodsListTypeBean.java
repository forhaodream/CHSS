package com.sheepshop.businessside.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * Created by CH
 * on 2019/12/26 10:03
 */
public class GoodsListTypeBean {

    /**
     * topMapNumList : {"1":2,"2":0,"3":1,"4":1}
     * goodsList : [{"id":18,"packageClassId":1,"packageClassName":"火锅类","name":"狗腿","showUrl":"http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg","attr":"1750","arg":"盘","stockStatus":0,"stockNum":50,"stockAutoStatus":1,"maxStockNum":50,"isRelease":1,"monthSales":0,"score":"0"},{"id":17,"packageClassId":1,"packageClassName":"火锅类","name":"猪腿","showUrl":"http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg","attr":"1750","arg":"盘","stockStatus":0,"stockNum":70,"stockAutoStatus":1,"maxStockNum":70,"isRelease":2,"monthSales":0,"score":"0"},{"id":16,"packageClassId":1,"packageClassName":"火锅类","name":"牛腿","showUrl":"http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg","attr":"1750","arg":"盘","stockStatus":0,"stockNum":50,"stockAutoStatus":1,"maxStockNum":50,"isRelease":0,"monthSales":0,"score":"0"},{"id":15,"packageClassId":1,"packageClassName":"火锅类","name":"羊腿","showUrl":"http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg","attr":"1750","arg":"盘","stockStatus":0,"stockNum":50,"stockAutoStatus":1,"maxStockNum":50,"isRelease":1,"monthSales":0,"score":"0"}]
     */

    private TopMapNumListBean topMapNumList;
    private List<GoodsListBean> goodsList;

    public TopMapNumListBean getTopMapNumList() {
        return topMapNumList;
    }

    public void setTopMapNumList(TopMapNumListBean topMapNumList) {
        this.topMapNumList = topMapNumList;
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public static class TopMapNumListBean {
        /**
         * 1 : 2
         * 2 : 0
         * 3 : 1
         * 4 : 1
         */

        @SerializedName("1")
        private int _$1;
        @SerializedName("2")
        private int _$2;
        @SerializedName("3")
        private int _$3;
        @SerializedName("4")
        private int _$4;

        public int get_$1() {
            return _$1;
        }

        public void set_$1(int _$1) {
            this._$1 = _$1;
        }

        public int get_$2() {
            return _$2;
        }

        public void set_$2(int _$2) {
            this._$2 = _$2;
        }

        public int get_$3() {
            return _$3;
        }

        public void set_$3(int _$3) {
            this._$3 = _$3;
        }

        public int get_$4() {
            return _$4;
        }

        public void set_$4(int _$4) {
            this._$4 = _$4;
        }
    }

    public static class GoodsListBean implements Serializable, Comparable {
        /**
         * id : 18
         * packageClassId : 1
         * packageClassName : 火锅类
         * name : 狗腿
         * showUrl : http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg
         * attr : 1750
         * arg : 盘
         * stockStatus : 0
         * stockNum : 50
         * stockAutoStatus : 1
         * maxStockNum : 50
         * isRelease : 1
         * monthSales : 0
         * score : 0
         */

        private int top;
        private long time;
        private int avatar;
        private int id;
        private int packageClassId;
        private String packageClassName;
        private String name;
        private String showUrl;
        private String attr;
        private String arg;
        private int stockStatus;
        private int stockNum;
        private int stockAutoStatus;
        private int maxStockNum;
        private int isRelease;
        private int monthSales;
        private String score;
        private String money;
        private String originalPrice;

        @Override
        public int compareTo(Object another) {
            if (another == null || !(another instanceof GoodsListBean)) {
                return -1;
            }

            GoodsListBean otherSession = (GoodsListBean) another;
            /**置顶判断 ArrayAdapter是按照升序从上到下排序的，就是默认的自然排序
             * 如果是相等的情况下返回0，包括都置顶或者都不置顶，返回0的情况下要
             * 再做判断，拿它们置顶时间进行判断
             * 如果是不相等的情况下，otherSession是置顶的，则当前session是非置顶的，应该在otherSession下面，所以返回1
             *  同样，session是置顶的，则当前otherSession是非置顶的，应该在otherSession上面，所以返回-1
             * */
            int result = 0 - (top - otherSession.getTop());
            if (result == 0) {
                result = 0 - compareToTime(time, otherSession.getTime());
            }
            return result;
        }

        /**
         * 根据时间对比
         */
        public static int compareToTime(long lhs, long rhs) {
            Calendar cLhs = Calendar.getInstance();
            Calendar cRhs = Calendar.getInstance();
            cLhs.setTimeInMillis(lhs);
            cRhs.setTimeInMillis(rhs);
            return cLhs.compareTo(cRhs);
        }

        public int getTop() {
            return top;
        }

        public void setTop(int top) {
            this.top = top;
        }

        public long getTime() {
            return time;
        }

        public void setTime(long time) {
            this.time = time;
        }

        public int getAvatar() {
            return avatar;
        }

        public void setAvatar(int avatar) {
            this.avatar = avatar;
        }

        public String getMoney() {
            return money == null ? "" : money;
        }

        public void setMoney(String money) {
            this.money = money == null ? "" : money;
        }

        public String getOriginalPrice() {
            return originalPrice == null ? "" : originalPrice;
        }

        public void setOriginalPrice(String originalPrice) {
            this.originalPrice = originalPrice == null ? "" : originalPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPackageClassId() {
            return packageClassId;
        }

        public void setPackageClassId(int packageClassId) {
            this.packageClassId = packageClassId;
        }

        public String getPackageClassName() {
            return packageClassName;
        }

        public void setPackageClassName(String packageClassName) {
            this.packageClassName = packageClassName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getShowUrl() {
            return showUrl;
        }

        public void setShowUrl(String showUrl) {
            this.showUrl = showUrl;
        }

        public String getAttr() {
            return attr;
        }

        public void setAttr(String attr) {
            this.attr = attr;
        }

        public String getArg() {
            return arg;
        }

        public void setArg(String arg) {
            this.arg = arg;
        }

        public int getStockStatus() {
            return stockStatus;
        }

        public void setStockStatus(int stockStatus) {
            this.stockStatus = stockStatus;
        }

        public int getStockNum() {
            return stockNum;
        }

        public void setStockNum(int stockNum) {
            this.stockNum = stockNum;
        }

        public int getStockAutoStatus() {
            return stockAutoStatus;
        }

        public void setStockAutoStatus(int stockAutoStatus) {
            this.stockAutoStatus = stockAutoStatus;
        }

        public int getMaxStockNum() {
            return maxStockNum;
        }

        public void setMaxStockNum(int maxStockNum) {
            this.maxStockNum = maxStockNum;
        }

        public int getIsRelease() {
            return isRelease;
        }

        public void setIsRelease(int isRelease) {
            this.isRelease = isRelease;
        }

        public int getMonthSales() {
            return monthSales;
        }

        public void setMonthSales(int monthSales) {
            this.monthSales = monthSales;
        }

        public String getScore() {
            return score;
        }

        public void setScore(String score) {
            this.score = score;
        }
    }
}
