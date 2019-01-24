package com.minmai.wallet.moudles.bean;

/**
 * 四要素返回数据
 */
public class Elements {

    private String name;

    private String cardNo;

    private String idNo;

    private String phoneNo;

    private String respMessage;

    private String respCode;

    private String bankName;

    private String bankKind;

    private String bankType;

    private String bankCode;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getRespMessage() {
        return respMessage;
    }

    public void setRespMessage(String respMessage) {
        this.respMessage = respMessage;
    }

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String respCode) {
        this.respCode = respCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankKind() {
        return bankKind;
    }

    public void setBankKind(String bankKind) {
        this.bankKind = bankKind;
    }

    public String getBankType() {
        return bankType;
    }

    public void setBankType(String bankType) {
        this.bankType = bankType;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "Elements{" +
                "name='" + name + '\'' +
                ", cardNo='" + cardNo + '\'' +
                ", idNo='" + idNo + '\'' +
                ", phoneNo='" + phoneNo + '\'' +
                ", respMessage='" + respMessage + '\'' +
                ", respCode='" + respCode + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankKind='" + bankKind + '\'' +
                ", bankType='" + bankType + '\'' +
                ", bankCode='" + bankCode + '\'' +
                '}';
    }
}
