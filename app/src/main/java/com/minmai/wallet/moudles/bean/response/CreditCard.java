package com.minmai.wallet.moudles.bean.response;

/**
 * 信用卡实体类
 */
public class CreditCard {


    private String creditId; //  出金卡主键
    private String carNumber; //  银行卡号
    private String bankName; // 银行名称
    private String type; // 卡类型，肯定是信用卡
    private String phone; // 预留手机号
    private String logo; // 银行LOGO图片
    private String bankBackground; // 银行背景虚化logo
    private String backgroundColor; // 银行背景颜色
    private String creditAlias; // 银行卡昵称
    private String bankColorLogo; // 银行卡彩色logo
    private String billDate; // 账单日
    private String repaymentDate; // 还款日

    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "creditId='" + creditId + '\'' +
                ", carNumber='" + carNumber + '\'' +
                ", bankName='" + bankName + '\'' +
                ", type='" + type + '\'' +
                ", phone='" + phone + '\'' +
                ", logo='" + logo + '\'' +
                ", bankBackground='" + bankBackground + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                ", creditAlias='" + creditAlias + '\'' +
                ", bankColorLogo='" + bankColorLogo + '\'' +
                ", billDate='" + billDate + '\'' +
                ", repaymentDate='" + repaymentDate + '\'' +
                '}';
    }

    public String getCreditId() {
        return creditId;
    }

    public void setCreditId(String creditId) {
        this.creditId = creditId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBankBackground() {
        return bankBackground;
    }

    public void setBankBackground(String bankBackground) {
        this.bankBackground = bankBackground;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getCreditAlias() {
        return creditAlias;
    }

    public void setCreditAlias(String creditAlias) {
        this.creditAlias = creditAlias;
    }

    public String getBankColorLogo() {
        return bankColorLogo;
    }

    public void setBankColorLogo(String bankColorLogo) {
        this.bankColorLogo = bankColorLogo;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getRepaymentDate() {
        return repaymentDate;
    }

    public void setRepaymentDate(String repaymentDate) {
        this.repaymentDate = repaymentDate;
    }
}
