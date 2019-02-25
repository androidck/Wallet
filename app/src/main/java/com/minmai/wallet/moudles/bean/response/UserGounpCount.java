package com.minmai.wallet.moudles.bean.response;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 相应等级
 */
public class UserGounpCount {

    /**
     * 总资产
     */
    private String totalBalance;
    /**
     * 分润
     */
    private String feMoney;
    /**
     * 佣金
     */
    private String yjMoney;
    /**
     * 商户人数
     */
    private String countByNum;

    public List<LevelUserGroupCountList> levelUserGroupCountList;

    public String getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(String totalBalance) {
        this.totalBalance = totalBalance;
    }

    public String getFeMoney() {
        return feMoney;
    }

    public void setFeMoney(String feMoney) {
        this.feMoney = feMoney;
    }

    public String getYjMoney() {
        return yjMoney;
    }

    public void setYjMoney(String yjMoney) {
        this.yjMoney = yjMoney;
    }

    public String getCountByNum() {
        return countByNum;
    }

    public void setCountByNum(String countByNum) {
        this.countByNum = countByNum;
    }

    public List<LevelUserGroupCountList> getLevelUserGroupCountList() {
        return levelUserGroupCountList;
    }

    public void setLevelUserGroupCountList(List<LevelUserGroupCountList> levelUserGroupCountList) {
        this.levelUserGroupCountList = levelUserGroupCountList;
    }

    class LevelUserGroupCountList implements Serializable {
        /**
         * 等级id
         */
        private String levelId;
        /**
         * 等级图片
         */
        private String levelIco;
        /**
         * 等级名称
         */
        private String levelName;
        /**
         * 等级人数
         */
        private String levelCount;

        public String getLevelId() {
            return levelId;
        }

        public void setLevelId(String levelId) {
            this.levelId = levelId;
        }

        public String getLevelIco() {
            return levelIco;
        }

        public void setLevelIco(String levelIco) {
            this.levelIco = levelIco;
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
    }

}
