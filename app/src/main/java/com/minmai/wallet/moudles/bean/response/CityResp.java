package com.minmai.wallet.moudles.bean.response;

public class CityResp {


    /**
     * name : 河北省
     * pId : 0
     * id : 130000
     */

    private String name;
    private String pId;
    private String id;

    @Override
    public String toString() {
        return "CityResp{" +
                "name='" + name + '\'' +
                ", pId='" + pId + '\'' +
                ", id='" + id + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPId() {
        return pId;
    }

    public void setPId(String pId) {
        this.pId = pId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
