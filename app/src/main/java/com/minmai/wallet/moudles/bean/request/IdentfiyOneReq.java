package com.minmai.wallet.moudles.bean.request;

/**
 * 实名信息认证第一部
 */
public class IdentfiyOneReq {

    private String userId;//用户id
    private String realName;//真实姓名
    private String idCard;//身份证号
    private String effectiveDate;//有效期
    private String cardFrontPic;//正面照片
    private String cardBackPic;//背面照片
    private String nation;//民族
    private String detailedAddress;//详细地址

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public String getCardFrontPic() {
        return cardFrontPic;
    }

    public void setCardFrontPic(String cardFrontPic) {
        this.cardFrontPic = cardFrontPic;
    }

    public String getCardBackPic() {
        return cardBackPic;
    }

    public void setCardBackPic(String cardBackPic) {
        this.cardBackPic = cardBackPic;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }
}
