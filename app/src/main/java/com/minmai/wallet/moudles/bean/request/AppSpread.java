package com.minmai.wallet.moudles.bean.request;

public class AppSpread{


    /**
     * id : 1
     * bigPicture : http://system.minmai1688.com/tuiguang01.jpg
     * smallPicture : http://system.minmai1688.com/01_small.png
     * qrcodeType : 2
     * qrcode : http://admin.minmai1688.com/H5/MinMai_wx/register.html?recommendCode=000335
     * createBy : 1
     * createDate : 1542183100000
     * updateBy : 1
     * updateDate : 1542183102000
     * remarks : 1
     * delFlag : 0
     */

    private String id;
    private String bigPicture;
    private String smallPicture;
    private String qrcodeType;
    private String qrcode;
    private String createBy;
    private long createDate;
    private String updateBy;
    private long updateDate;
    private String remarks;
    private String delFlag;
    private boolean isSelect;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    @Override
    public String toString() {
        return "AppSpread{" +
                "id='" + id + '\'' +
                ", bigPicture='" + bigPicture + '\'' +
                ", smallPicture='" + smallPicture + '\'' +
                ", qrcodeType='" + qrcodeType + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", createBy='" + createBy + '\'' +
                ", createDate=" + createDate +
                ", updateBy='" + updateBy + '\'' +
                ", updateDate=" + updateDate +
                ", remarks='" + remarks + '\'' +
                ", delFlag='" + delFlag + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBigPicture() {
        return bigPicture;
    }

    public void setBigPicture(String bigPicture) {
        this.bigPicture = bigPicture;
    }

    public String getSmallPicture() {
        return smallPicture;
    }

    public void setSmallPicture(String smallPicture) {
        this.smallPicture = smallPicture;
    }

    public String getQrcodeType() {
        return qrcodeType;
    }

    public void setQrcodeType(String qrcodeType) {
        this.qrcodeType = qrcodeType;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(long createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
}