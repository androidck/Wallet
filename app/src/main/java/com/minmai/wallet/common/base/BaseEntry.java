package com.minmai.wallet.common.base;

import com.minmai.wallet.common.uitl.MainUtil;

import java.math.BigDecimal;

/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description:
 */

public class BaseEntry<T> {

    private int code;
    private String msg;

    private T data;




    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        return getCode()== MainUtil.SUCCESS_CODE;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BaseEntry{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

}
