package com.sheepshop.businessside.entity;

import java.util.List;

/**
 * Created by CH
 * on 2019/12/19 11:16
 */
public class BankListBean {


    /**
     * bankFirst : JT
     * bankName : 交通银行
     * bankShort : COMM
     */

    private String bankFirst;
    private String bankName;
    private String bankShort;

    public String getBankFirst() {
        return bankFirst;
    }

    public void setBankFirst(String bankFirst) {
        this.bankFirst = bankFirst;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankShort() {
        return bankShort;
    }

    public void setBankShort(String bankShort) {
        this.bankShort = bankShort;
    }
}

