package com.minmai.wallet.moudles.bean.response;

/**
 * 交易记录
 */
public class Trade {


    /**
     * consumptiongSeq : 6181129164918256
     * tradingAmount : 1561.0000
     * creditId : 00e53c600d36476a90bae375e29801cf
     * creditCard : 6253360019014819
     * tradingStatus : 3
     * tradingDate : 2018-11-29 16:49:18.0
     * bankName : 农业银行
     */

    private String consumptiongSeq;
    private String tradingAmount;
    private String creditId;
    private String creditCard;
    private String tradingStatus;
    private String tradingDate;
    private String bankName;

    public String getConsumptiongSeq() {
        return consumptiongSeq;
    }

    public void setConsumptiongSeq(String consumptiongSeq) {
        this.consumptiongSeq = consumptiongSeq;
    }

    public String getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(String tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getTradingStatus() {
        return tradingStatus;
    }

    public void setTradingStatus(String tradingStatus) {
        this.tradingStatus = tradingStatus;
    }

    public String getTradingDate() {
        return tradingDate;
    }

    public void setTradingDate(String tradingDate) {
        this.tradingDate = tradingDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}
