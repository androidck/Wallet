package com.minmai.wallet.moudles.bean.response;

/**
 * 通道限额
 */
public class ChannelBank {

    private String bankId;		// 银行ID
    private String cardType;		// 卡片类型
    private String tradingLimitUp;		// 交易上限
    private String tradingLimitLow;		// 交易下限
    private String dailyLimit;		// 当日限额
    private String monthLimit;		// 当月限额
    private String channelId;		// 通道编号

    private String tradingLimitUpY;		// 交易上限
    private String tradingLimitLowY;		// 交易下限
    private String dailyLimitY;		// 当日限额
    private String monthLimitY;		// 当月限额

    @Override
    public String toString() {
        return "ChannelBank{" +
                "bankId='" + bankId + '\'' +
                ", cardType='" + cardType + '\'' +
                ", tradingLimitUp='" + tradingLimitUp + '\'' +
                ", tradingLimitLow='" + tradingLimitLow + '\'' +
                ", dailyLimit='" + dailyLimit + '\'' +
                ", monthLimit='" + monthLimit + '\'' +
                ", channelId='" + channelId + '\'' +
                ", tradingLimitUpY='" + tradingLimitUpY + '\'' +
                ", tradingLimitLowY='" + tradingLimitLowY + '\'' +
                ", dailyLimitY='" + dailyLimitY + '\'' +
                ", monthLimitY='" + monthLimitY + '\'' +
                '}';
    }

    public String getBankId() {
        return bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getTradingLimitUp() {
        return tradingLimitUp;
    }

    public void setTradingLimitUp(String tradingLimitUp) {
        this.tradingLimitUp = tradingLimitUp;
    }

    public String getTradingLimitLow() {
        return tradingLimitLow;
    }

    public void setTradingLimitLow(String tradingLimitLow) {
        this.tradingLimitLow = tradingLimitLow;
    }

    public String getDailyLimit() {
        return dailyLimit;
    }

    public void setDailyLimit(String dailyLimit) {
        this.dailyLimit = dailyLimit;
    }

    public String getMonthLimit() {
        return monthLimit;
    }

    public void setMonthLimit(String monthLimit) {
        this.monthLimit = monthLimit;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getTradingLimitUpY() {
        return tradingLimitUpY;
    }

    public void setTradingLimitUpY(String tradingLimitUpY) {
        this.tradingLimitUpY = tradingLimitUpY;
    }

    public String getTradingLimitLowY() {
        return tradingLimitLowY;
    }

    public void setTradingLimitLowY(String tradingLimitLowY) {
        this.tradingLimitLowY = tradingLimitLowY;
    }

    public String getDailyLimitY() {
        return dailyLimitY;
    }

    public void setDailyLimitY(String dailyLimitY) {
        this.dailyLimitY = dailyLimitY;
    }

    public String getMonthLimitY() {
        return monthLimitY;
    }

    public void setMonthLimitY(String monthLimitY) {
        this.monthLimitY = monthLimitY;
    }
}
