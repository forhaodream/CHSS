package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2020/1/2 14:16
 */
public class GoodsDetailBean {

    /**
     * id : 18
     * odId : 1
     * packageClassId : 1
     * packageClassName : 火锅
     * goodsClassId : 1
     * goodsClassName : 荤菜类
     * name : 狗腿
     * details : 好吃不上火,可是大好机会空间的时候看见的时候看见的时候就开始的方法的手机壳好好的上课就发货的时间可付费的手机壳好好的手机壳发货速度加快
     * showUrl : http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg
     * mainUrl : http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg,http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg,http://image.lnhcsk.com/shop/goods/20191115/365846bf8163424486331378d25b8828.jpg
     * money : 19998
     * originalPrice : 0
     * attr : 1750
     * arg : 盘
     * stockStatus : 1
     * stockNum : 123
     * stockAutoStatus : 0
     * maxStockNum : 50
     * boxNum : 1
     * boxTotalPrice : 200
     * isRelease : 1
     * isRecommend : 0
     * version : 3
     * monthSales : 8
     * totalSales : 0
     * score : 0
     * labelIds : 12
     * labelList : [{"id":12,"name":"超级好吃","type":"3","wordColorCode":"#ffffff","bgColorCode":"#c7326d","status":0,"createTime":"2019-12-25T08:28:49.000+0000"}]
     * createTime : 2019-12-20T03:31:11.000+0000
     * deleted : 0
     */

    private int id;
    private int odId;
    private int packageClassId;
    private String packageClassName;
    private int goodsClassId;
    private String goodsClassName;
    private String name;
    private String details;
    private String showUrl;
    private String mainUrl;
    private String money;
    private String originalPrice;
    private String attr;
    private String arg;
    private int stockStatus;
    private int stockNum;
    private int stockAutoStatus;
    private int maxStockNum;
    private int boxNum;
    private String boxTotalPrice;
    private int isRelease;
    private int isRecommend;
    private int version;
    private int monthSales;
    private int totalSales;
    private String score;
    private String labelIds;
    private String createTime;
    private int deleted;
    private List<LabelListBean> labelList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOdId() {
        return odId;
    }

    public void setOdId(int odId) {
        this.odId = odId;
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

    public int getGoodsClassId() {
        return goodsClassId;
    }

    public void setGoodsClassId(int goodsClassId) {
        this.goodsClassId = goodsClassId;
    }

    public String getGoodsClassName() {
        return goodsClassName;
    }

    public void setGoodsClassName(String goodsClassName) {
        this.goodsClassName = goodsClassName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getShowUrl() {
        return showUrl;
    }

    public void setShowUrl(String showUrl) {
        this.showUrl = showUrl;
    }

    public String getMainUrl() {
        return mainUrl;
    }

    public void setMainUrl(String mainUrl) {
        this.mainUrl = mainUrl;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(String originalPrice) {
        this.originalPrice = originalPrice;
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

    public int getBoxNum() {
        return boxNum;
    }

    public void setBoxNum(int boxNum) {
        this.boxNum = boxNum;
    }

    public String getBoxTotalPrice() {
        return boxTotalPrice;
    }

    public void setBoxTotalPrice(String boxTotalPrice) {
        this.boxTotalPrice = boxTotalPrice;
    }

    public int getIsRelease() {
        return isRelease;
    }

    public void setIsRelease(int isRelease) {
        this.isRelease = isRelease;
    }

    public int getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(int isRecommend) {
        this.isRecommend = isRecommend;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getMonthSales() {
        return monthSales;
    }

    public void setMonthSales(int monthSales) {
        this.monthSales = monthSales;
    }

    public int getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(int totalSales) {
        this.totalSales = totalSales;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(String labelIds) {
        this.labelIds = labelIds;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getDeleted() {
        return deleted;
    }

    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }

    public List<LabelListBean> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<LabelListBean> labelList) {
        this.labelList = labelList;
    }

    public static class LabelListBean {
        /**
         * id : 12
         * name : 超级好吃
         * type : 3
         * wordColorCode : #ffffff
         * bgColorCode : #c7326d
         * status : 0
         * createTime : 2019-12-25T08:28:49.000+0000
         */

        private int id;
        private String name;
        private String type;
        private String wordColorCode;
        private String bgColorCode;
        private int status;
        private String createTime;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWordColorCode() {
            return wordColorCode;
        }

        public void setWordColorCode(String wordColorCode) {
            this.wordColorCode = wordColorCode;
        }

        public String getBgColorCode() {
            return bgColorCode;
        }

        public void setBgColorCode(String bgColorCode) {
            this.bgColorCode = bgColorCode;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }
    }
}
