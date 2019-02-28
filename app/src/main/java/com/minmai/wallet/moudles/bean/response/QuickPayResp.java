package com.minmai.wallet.moudles.bean.response;

public class QuickPayResp {

    private int code; // 判断接口是否调用成功
    // 200 成功  311 通道异常    312通道未响应   313修改储蓄卡异常   314 开卡异常   315 验签失败  400参数有误

    // 交易成功的情况下返回：
    private String createPayType; // 1代表开通快捷支付   2代表交易
    private String codeUrl; // 用户访问地址，当createPayType为1时，为开通快捷支付链接，当类型为2时，为交易链接
    private String payState;//交易状态  PAID:交易成功  WAITING:需要开卡  CANCELED_OR_TIMEOUT： 交易取消或超时  其他代表未交易

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getCreatePayType() {
        return createPayType;
    }

    public void setCreatePayType(String createPayType) {
        this.createPayType = createPayType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPayState() {
        return payState;
    }

    public void setPayState(String payState) {
        this.payState = payState;
    }

    @Override
    public String toString() {
        return "QuickPayResp{" +
                "code=" + code +
                ", createPayType='" + createPayType + '\'' +
                ", codeUrl='" + codeUrl + '\'' +
                ", payState='" + payState + '\'' +
                '}';
    }
}
