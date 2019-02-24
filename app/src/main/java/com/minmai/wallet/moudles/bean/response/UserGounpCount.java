package com.minmai.wallet.moudles.bean.response;

import java.math.BigDecimal;
import java.util.List;

/**
 * 相应等级
 */
public class UserGounpCount {

    /**
     * levelId : 111
     * levelName : 黄金
     * levelCount : 1
     * levelIco :
     */
    private BigDecimal totalBalance;//总金额

    private int countByNum;//总人数

    private BigDecimal feMoney;//分润金额

    private BigDecimal yjMoney;//佣金金额

    private List<Grade> list;//嵌套的列表

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public int getCountByNum() {
        return countByNum;
    }

    public void setCountByNum(int countByNum) {
        this.countByNum = countByNum;
    }

    public BigDecimal getFeMoney() {
        return feMoney;
    }

    public void setFeMoney(BigDecimal feMoney) {
        this.feMoney = feMoney;
    }

    public BigDecimal getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(BigDecimal yjMoney) {
        this.yjMoney = yjMoney;
    }

    public List<Grade> getList() {
        return list;
    }

    public void setList(List<Grade> list) {
        this.list = list;
    }

    public class Grade{
        private String levelId;
        private String levelName;
        private String levelCount;
        private String levelIco;

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getLevelName() {
            return levelName;
        }

        public void setLevelName(String levelName) {
            this.levelName = levelName;
        }

        public String getLevelCount() {
            return levelCount;
        }

        public void setLevelCount(String levelCount) {
            this.levelCount = levelCount;
        }

        public String getLevelIco() {
            return levelIco;
        }

        public void setLevelIco(String levelIco) {
            this.levelIco = levelIco;
        }
    }




}
