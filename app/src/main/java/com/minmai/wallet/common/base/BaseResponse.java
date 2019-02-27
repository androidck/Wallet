package com.minmai.wallet.common.base;

public class BaseResponse<T> {

    private int code;
    private String msg;
    private Data<T> data;

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

    public Data<T> getData() {
        return data;
    }

    public void setData(Data<T> data) {
        this.data = data;
    }

    public class Data<T>{
        private T list;

        public T getList() {
            return list;
        }

        public void setList(T list) {
            this.list = list;
        }
    }
}
