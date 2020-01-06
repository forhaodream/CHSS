package com.sheepshop.businessside.ui.bean;

import java.io.File;

/**
 * Created by CH
 * on 2019/12/17 10:36
 */
public class CenterInfoBean {
    // 店铺名称
    private String shopName;
    //店主姓名
    private String shopOwner;
    // 联系电话
    private String shopTel;
    // 店铺类型
    private int shopKinds;
    // 店铺地址
    private String shopAddress;
    // 营业时间
    private String openTime;
    // 休息时间
    private String closeTime;
    // 身份证正面
    private File idCardFront;
    // 身份证反面
    private File idCardBehind;
    // 营业执照
    private File license;
    //  许可证
    private File licence;

    private int shopId;

    private double latitude;
    private double longitude;

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName == null ? "" : shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? "" : shopName;
    }

    public String getShopOwner() {
        return shopOwner == null ? "" : shopOwner;
    }

    public void setShopOwner(String shopOwner) {
        this.shopOwner = shopOwner == null ? "" : shopOwner;
    }

    public String getShopTel() {
        return shopTel == null ? "" : shopTel;
    }

    public void setShopTel(String shopTel) {
        this.shopTel = shopTel == null ? "" : shopTel;
    }

    public int getShopKinds() {
        return shopKinds;
    }

    public void setShopKinds(int shopKinds) {
        this.shopKinds = shopKinds;
    }

    public String getShopAddress() {
        return shopAddress == null ? "" : shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? "" : shopAddress;
    }

    public String getOpenTime() {
        return openTime == null ? "" : openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? "" : openTime;
    }

    public String getCloseTime() {
        return closeTime == null ? "" : closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime == null ? "" : closeTime;
    }

    public File getIdCardFront() {
        return idCardFront;
    }

    public void setIdCardFront(File idCardFront) {
        this.idCardFront = idCardFront;
    }

    public File getIdCardBehind() {
        return idCardBehind;
    }

    public void setIdCardBehind(File idCardBehind) {
        this.idCardBehind = idCardBehind;
    }

    public File getLicense() {
        return license;
    }

    public void setLicense(File license) {
        this.license = license;
    }

    public File getLicence() {
        return licence;
    }

    public void setLicence(File licence) {
        this.licence = licence;
    }
}
