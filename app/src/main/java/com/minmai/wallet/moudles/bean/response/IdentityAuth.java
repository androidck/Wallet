package com.minmai.wallet.moudles.bean.response;

/**
 * 实名信息实体类
 */
public class IdentityAuth {

    /**
     * remarks :
     * createDate : 2018-11-30 22:00:09
     * updateDate : 2018-12-03 11:51:44
     * id : 171304c81da3439db5d7d2fe063f1dbc
     * realName : 贾彦平
     * idCard : 620422199002121911
     * effectiveDate : 20140815-20240815
     * cardFrontPic : http://img.minmai1688.com/icon_20181130220001
     * cardBackPic : http://img.minmai1688.com/icon_20181130220008
     * handIdCard : http://img.minmai1688.com/img_1543586423469
     * userId : 73f1172a9a1b4ab6addfce5576ec8853
     * province : 甘肃
     * city : 白银市
     * area : 会宁县
     * detailedAddress : 济南市历下区舜华路1000号
     * birthday : 634752000000
     * nation : 汉
     */

    private String remarks;
    private String createDate;
    private String updateDate;
    private String id;
    private String realName;
    private String idCard;
    private String effectiveDate;
    private String cardFrontPic;
    private String cardBackPic;
    private String handIdCard;
    private String userId;
    private String province;
    private String city;
    private String area;
    private String detailedAddress;
    private long birthday;
    private String nation;

    @Override
    public String toString() {
        return "IdentityAuth{" +
                "remarks='" + remarks + '\'' +
                ", createDate='" + createDate + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", id='" + id + '\'' +
                ", realName='" + realName + '\'' +
                ", idCard='" + idCard + '\'' +
                ", effectiveDate='" + effectiveDate + '\'' +
                ", cardFrontPic='" + cardFrontPic + '\'' +
                ", cardBackPic='" + cardBackPic + '\'' +
                ", handIdCard='" + handIdCard + '\'' +
                ", userId='" + userId + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", detailedAddress='" + detailedAddress + '\'' +
                ", birthday=" + birthday +
                ", nation='" + nation + '\'' +
                '}';
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getHandIdCard() {
        return handIdCard;
    }

    public void setHandIdCard(String handIdCard) {
        this.handIdCard = handIdCard;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDetailedAddress() {
        return detailedAddress;
    }

    public void setDetailedAddress(String detailedAddress) {
        this.detailedAddress = detailedAddress;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }
}
