package com.minmai.wallet.moudles.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 数据库存储银行总行列表
 */
@Entity
public class DbBankInfo {

    @Id(autoincrement = true)
    private Long id;

    private String bankId;

    private String bankName;//银行名称

    private String bankShortName;//银行引文缩写

    @Generated(hash = 93037093)
    public DbBankInfo(Long id, String bankId, String bankName,
            String bankShortName) {
        this.id = id;
        this.bankId = bankId;
        this.bankName = bankName;
        this.bankShortName = bankShortName;
    }



    @Generated(hash = 1844500705)
    public DbBankInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBankId() {
        return this.bankId;
    }

    public void setBankId(String bankId) {
        this.bankId = bankId;
    }

    public String getBankName() {
        return this.bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankShortName() {
        return this.bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }

    @Override
    public String toString() {
        return "DbBankInfo{" +
                "id=" + id +
                ", bankId='" + bankId + '\'' +
                ", bankName='" + bankName + '\'' +
                ", bankShortName='" + bankShortName + '\'' +
                '}';
    }
}
