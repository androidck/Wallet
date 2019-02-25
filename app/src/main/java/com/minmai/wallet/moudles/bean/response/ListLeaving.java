package com.minmai.wallet.moudles.bean.response;

public class ListLeaving {


    /**
     * id : 300aa07c94d34a08809f8cb0a9f9225a
     * leaveMessageContent : 努力工作
     * updateDate : 1543483355000
     * readState : 1
     * userNo : 000792
     * userHead : http://img.minmai1688.com/mmexport1537171769754
     * phone : 13921441102
     */

    private String id;
    private String leaveMessageContent;
    private long updateDate;
    private String readState;
    private String userNo;
    private String userHead;
    private String phone;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLeaveMessageContent() {
        return leaveMessageContent;
    }

    public void setLeaveMessageContent(String leaveMessageContent) {
        this.leaveMessageContent = leaveMessageContent;
    }

    public long getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(long updateDate) {
        this.updateDate = updateDate;
    }

    public String getReadState() {
        return readState;
    }

    public void setReadState(String readState) {
        this.readState = readState;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
