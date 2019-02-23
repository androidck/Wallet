package com.minmai.wallet.moudles.bean.response;

public class RollMessage {

    /**
     * id : 1
     * message : 恭喜尾号为7763的用户升级成功
     * createBy : 1
     * createDate : 1543493932000
     * updateBy : 1
     * updateDate : 1543493936000
     * delFlag : 0
     * remark : 1
     */

    private String id;
    private String message;
    private String createBy;
    private long createDate;
    private String updateBy;
    private long updateDate;
    private String delFlag;
    private String remark;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
