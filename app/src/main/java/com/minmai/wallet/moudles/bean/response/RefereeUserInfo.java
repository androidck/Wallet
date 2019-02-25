package com.minmai.wallet.moudles.bean.response;

/**
 * 推荐人信息
 */
public class RefereeUserInfo {


    /**
     * refereeId : 推荐人id
     * userHead : url
     * userNo : 10000001
     * nickName : 昵称
     * levelName : 等级名称
     */

    private String refereeId;
    private String userHead;
    private String userNo;
    private String nickName;
    private String levelName;

    @Override
    public String toString() {
        return "RefereeUserInfo{" +
                "refereeId='" + refereeId + '\'' +
                ", userHead='" + userHead + '\'' +
                ", userNo='" + userNo + '\'' +
                ", nickName='" + nickName + '\'' +
                ", levelName='" + levelName + '\'' +
                '}';
    }

    public String getRefereeId() {
        return refereeId;
    }

    public void setRefereeId(String refereeId) {
        this.refereeId = refereeId;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserNo() {
        return userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }
}
