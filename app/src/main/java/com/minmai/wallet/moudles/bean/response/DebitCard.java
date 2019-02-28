package com.minmai.wallet.moudles.bean.response;

/**
 * 储蓄卡实体类
 */
public class DebitCard {


    /**
     * carNumber : 6222021602027828336
     * phone : 15588837763
     * bankBackground : http://system.minmai1688.com/gongshang_logo_big.png
     * isDefault : 1
     * logo : http://system.minmai1688.com/gongshang_logo.png
     * bankName : 工商银行
     * type : 储蓄卡
     * realName : 贾彦平
     * backgroundColor : #e60012
     * bankColorLogo : http://system.minmai1688.com/gongshang.png
     * debitCardId : b97ca6c21ec548dd82c3eefeba8972aa
     */

    private String carNumber;
    private String phone;
    private String bankBackground;
    private String isDefault;
    private String logo;
    private String bankName;
    private String type;
    private String realName;
    private String backgroundColor;
    private String bankColorLogo;
    private String debitCardId;

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBankBackground() {
        return bankBackground;
    }

    public void setBankBackground(String bankBackground) {
        this.bankBackground = bankBackground;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getBankColorLogo() {
        return bankColorLogo;
    }

    public void setBankColorLogo(String bankColorLogo) {
        this.bankColorLogo = bankColorLogo;
    }

    public String getDebitCardId() {
        return debitCardId;
    }

    public void setDebitCardId(String debitCardId) {
        this.debitCardId = debitCardId;
    }
}
