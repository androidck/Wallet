package com.minmai.wallet.moudles.bean.request;

public class QuickPayReq {

    private String channelId; // 通道Id,用于识别用户选择的通道

    private String money; // 刷卡金额

    private String debitCardId; // 入金卡ID  储蓄卡

    private String creditCardId; // 出金卡ID 信用卡

    private String userId; // 用户id

    private String returnUrl; // 前台跳转地址

    private String city; // 前台获取城市   省，市，370102

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(String creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
