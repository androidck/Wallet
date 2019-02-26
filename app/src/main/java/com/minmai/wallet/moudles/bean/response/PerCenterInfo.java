package com.minmai.wallet.moudles.bean.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 个人中心实体类
 */
public class PerCenterInfo {

    private String userNo; // 用户编号
    private String userHead; // 用户头像
    private String nickName; // 用户昵称
    private String integralCalculus; // 用户积分
    private String membersLevelId; //用户等级
    private String levelName; // 等级名称
    private String refereeUser; // 推荐人
    private String debitCardCount; // 储蓄卡数量
    private String creditCardCount; // 信用卡数量
    private String telephone; // 客服电话
    private String needTo; // 待办

    private String extendOne; // 是否向下级展示手机号 1 是 2 否
    private String extendTwo; // 是否允许补录信息1是2否
    private String refereePhone; // 推荐人手机号
    private String refereeExtendOne; // 是否展示推荐人手机号  1 是 2 否

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getIntegralCalculus() {
        return integralCalculus;
    }

    public void setIntegralCalculus(String integralCalculus) {
        this.integralCalculus = integralCalculus;
    }

    public String getMembersLevelId() {
        return membersLevelId;
    }

    public void setMembersLevelId(String membersLevelId) {
        this.membersLevelId = membersLevelId;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public String getRefereeUser() {
        return refereeUser;
    }

    public void setRefereeUser(String refereeUser) {
        this.refereeUser = refereeUser;
    }

    public String getDebitCardCount() {
        return debitCardCount;
    }

    public void setDebitCardCount(String debitCardCount) {
        this.debitCardCount = debitCardCount;
    }

    public String getCreditCardCount() {
        return creditCardCount;
    }

    public void setCreditCardCount(String creditCardCount) {
        this.creditCardCount = creditCardCount;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNeedTo() {
        return needTo;
    }

    public void setNeedTo(String needTo) {
        this.needTo = needTo;
    }

    public String getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
    }

    public String getExtendTwo() {
        return extendTwo;
    }

    public void setExtendTwo(String extendTwo) {
        this.extendTwo = extendTwo;
    }

    public String getRefereePhone() {
        return refereePhone;
    }

    public void setRefereePhone(String refereePhone) {
        this.refereePhone = refereePhone;
    }

    public String getRefereeExtendOne() {
        return refereeExtendOne;
    }

    public void setRefereeExtendOne(String refereeExtendOne) {
        this.refereeExtendOne = refereeExtendOne;
    }
}
