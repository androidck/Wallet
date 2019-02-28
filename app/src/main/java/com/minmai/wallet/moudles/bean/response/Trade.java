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

    private String consumptiongSeq; // 流水号
    private String tradingAmount; // 交易金额
    private String creditId; // 信用卡ID
    private String creditCard; // 信用卡卡号
    private String tradingStatus; // 交易状态 交易状态1.交易成功、2.交易失败、3.交易申请中 4 其他状态
    private String tradingDate; // 交易时间
    private String bankName; // 银行名称
    private String extendedField1; //状态为4的展示的内容  保存交易中除了成功失败之外的状态
    private String channelName; //通道名称

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

    public String getExtendedField1() {
        return extendedField1;
    }

    public void setExtendedField1(String extendedField1) {
        this.extendedField1 = extendedField1;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }
}
