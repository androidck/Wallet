package com.minmai.wallet.moudles.bean.response;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class RegisterStateResp {

    @Id(autoincrement = true)
    private Long id;

    private String registerState;//进件状态

    private String isOpenDateRepayment;//是否开启日期还款

    @Generated(hash = 552263901)
    public RegisterStateResp(Long id, String registerState,
            String isOpenDateRepayment) {
        this.id = id;
        this.registerState = registerState;
        this.isOpenDateRepayment = isOpenDateRepayment;
    }

    @Generated(hash = 1285538347)
    public RegisterStateResp() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegisterState() {
        return this.registerState;
    }

    public void setRegisterState(String registerState) {
        this.registerState = registerState;
    }

    public String getIsOpenDateRepayment() {
        return this.isOpenDateRepayment;
    }

    public void setIsOpenDateRepayment(String isOpenDateRepayment) {
        this.isOpenDateRepayment = isOpenDateRepayment;
    }


}
