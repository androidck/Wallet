package com.minmai.wallet.moudles.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DbUserInfo {

    @Id
    private Long id;

    private String userId;

    private String phone;

    @Generated(hash = 870601090)
    public DbUserInfo(Long id, String userId, String phone) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }





    
}
