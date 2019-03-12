package com.minmai.wallet.moudles.db;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class DbCenterInfo {

    @Id(autoincrement = true)
    private Long id;

    private String userId;

    private String extendOne;

    private String userNo;

    private String nickName;

    @Generated(hash = 212599910)
    public DbCenterInfo(Long id, String userId, String extendOne, String userNo,
            String nickName) {
        this.id = id;
        this.userId = userId;
        this.extendOne = extendOne;
        this.userNo = userNo;
        this.nickName = nickName;
    }

    @Generated(hash = 1291668564)
    public DbCenterInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExtendOne() {
        return this.extendOne;
    }

    public void setExtendOne(String extendOne) {
        this.extendOne = extendOne;
    }

    public String getUserNo() {
        return this.userNo;
    }

    public void setUserNo(String userNo) {
        this.userNo = userNo;
    }

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    
}
