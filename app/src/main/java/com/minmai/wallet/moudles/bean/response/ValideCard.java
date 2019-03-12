package com.minmai.wallet.moudles.bean.response;

import java.util.List;

/**
 * 验证银行卡
 */
public class ValideCard {


    /**
     * cardType : CC
     * bank : CMBC
     * key : 1551424779576-1294-11.234.230.103-1922352038
     * messages : []
     * validated : true
     * stat : ok
     */

    private String cardType;
    private String bank;
    private String key;
    private boolean validated;
    private String stat;
    private List<?> messages;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isValidated() {
        return validated;
    }

    public void setValidated(boolean validated) {
        this.validated = validated;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public List<?> getMessages() {
        return messages;
    }

    public void setMessages(List<?> messages) {
        this.messages = messages;
    }
}
