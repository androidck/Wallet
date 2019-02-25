package com.minmai.wallet.moudles.bean.request;

/**
 * 给推荐人留言
 */
public class LeaveMessageReq {


    private String refereeId;//推荐人id

    private String leaveMessageContent;//留言内容

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getLeaveMessageContent() {
        return leaveMessageContent;
    }

    public void setLeaveMessageContent(String leaveMessageContent) {
        this.leaveMessageContent = leaveMessageContent;
    }
}
