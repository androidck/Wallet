package com.minmai.wallet.moudles.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class DbUserInfo {

    @Id(autoincrement = true)
    private Long id;

    private String userId;

    private String phone;

    private int registerState;

    @Generated(hash = 2131077017)
    public DbUserInfo(Long id, String userId, String phone, int registerState) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.registerState = registerState;
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

    public int getRegisterState() {
        return this.registerState;
    }

    public void setRegisterState(int registerState) {
        this.registerState = registerState;
    }

   





    
}
