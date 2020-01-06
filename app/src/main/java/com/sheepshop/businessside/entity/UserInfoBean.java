package com.sheepshop.businessside.entity;

/**
 * Created by CH
 * on 2019/11/14 15:11
 */
public class UserInfoBean {

    private int buId;
    private String buName;
    private String buMobile;
    private String buDetailId;
    private String buToken;
    private String buTime;
    private int buState;
    private int isFirstLogin;
    private int buStoreType;

    private String buRefuseReason;
    private String buOperationId;
    private String operationRefuseReason;
    private int buOperationType;

    public String getBuRefuseReason() {
        return buRefuseReason == null ? "" : buRefuseReason;
    }

    public void setBuRefuseReason(String buRefuseReason) {
        this.buRefuseReason = buRefuseReason == null ? "" : buRefuseReason;
    }

    public String getBuOperationId() {
        return buOperationId == null ? "" : buOperationId;
    }

    public void setBuOperationId(String buOperationId) {
        this.buOperationId = buOperationId == null ? "" : buOperationId;
    }

    public String getOperationRefuseReason() {
        return operationRefuseReason == null ? "" : operationRefuseReason;
    }

    public void setOperationRefuseReason(String operationRefuseReason) {
        this.operationRefuseReason = operationRefuseReason == null ? "" : operationRefuseReason;
    }

    public int getBuOperationType() {
        return buOperationType;
    }

    public void setBuOperationType(int buOperationType) {
        this.buOperationType = buOperationType;
    }

    public int getBuId() {
        return buId;
    }

    public void setBuId(int buId) {
        this.buId = buId;
    }

    public String getBuName() {
        return buName;
    }

    public void setBuName(String buName) {
        this.buName = buName;
    }

    public String getBuMobile() {
        return buMobile;
    }

    public void setBuMobile(String buMobile) {
        this.buMobile = buMobile;
    }

    public String getBuDetailId() {
        return buDetailId;
    }

    public void setBuDetailId(String buDetailId) {
        this.buDetailId = buDetailId;
    }

    public String getBuToken() {
        return buToken;
    }

    public void setBuToken(String buToken) {
        this.buToken = buToken;
    }

    public String getBuTime() {
        return buTime;
    }

    public void setBuTime(String buTime) {
        this.buTime = buTime;
    }

    public int getBuState() {
        return buState;
    }

    public void setBuState(int buState) {
        this.buState = buState;
    }

    public int getIsFirstLogin() {
        return isFirstLogin;
    }

    public void setIsFirstLogin(int isFirstLogin) {
        this.isFirstLogin = isFirstLogin;
    }

    public int getBuStoreType() {
        return buStoreType;
    }

    public void setBuStoreType(int buStoreType) {
        this.buStoreType = buStoreType;
    }
}


