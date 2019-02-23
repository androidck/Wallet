package com.minmai.wallet.moudles.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DbUserInfo {

    @Id(autoincrement = true)
    private Long id;

    private String userId;

    @Generated(hash = 2031681393)
    public DbUserInfo(Long id, String userId) {
        this.id = id;
        this.userId = userId;
    }

    @Generated(hash = 1930983001)
    public DbUserInfo() {
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

    
}
