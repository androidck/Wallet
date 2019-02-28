package com.minmai.wallet.moudles.bean.response;

import java.math.BigDecimal;

/**
 * 通道尸体类
 */
public class Channel {

    private String channelId; // 通道编号
    private String channelName;// 通道名称
    private String channelSingleLimitUp;// 通道单笔上限
    private String rate; // 通道费率
    private String channelType;// 1 刷卡 2代付
    private String supportBanks;// 支持银行
    private String fee; // 单笔手续费
    private String scoreFlag; // 是否有积分   1 ：有积分   2：没积分
    private String extendedField1; // 商户类型

    @Override
    public String toString() {
        return "Channel{" +
                "channelId='" + channelId + '\'' +
                ", channelName='" + channelName + '\'' +
                ", channelSingleLimitUp='" + channelSingleLimitUp + '\'' +
                ", rate='" + rate + '\'' +
                ", channelType='" + channelType + '\'' +
                ", supportBanks='" + supportBanks + '\'' +
                ", fee=" + fee +
                ", scoreFlag='" + scoreFlag + '\'' +
                ", extendedField1='" + extendedField1 + '\'' +
                ", isCheck=" + isCheck +
                '}';
    }

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelSingleLimitUp() {
        return channelSingleLimitUp;
    }

    public void setChannelSingleLimitUp(String channelSingleLimitUp) {
        this.channelSingleLimitUp = channelSingleLimitUp;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getChannelType() {
        return channelType;
    }

    public void setChannelType(String channelType) {
        this.channelType = channelType;
    }

    public String getSupportBanks() {
        return supportBanks;
    }

    public void setSupportBanks(String supportBanks) {
        this.supportBanks = supportBanks;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getScoreFlag() {
        return scoreFlag;
    }

    public void setScoreFlag(String scoreFlag) {
        this.scoreFlag = scoreFlag;
    }

    public String getExtendedField1() {
        return extendedField1;
    }

    public void setExtendedField1(String extendedField1) {
        this.extendedField1 = extendedField1;
    }
}
