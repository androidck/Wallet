package com.minmai.wallet.moudles.bean.response;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

public class BannerInfo extends SimpleBannerInfo {



    private String imageUrl;
    private String link;
    private String type;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BannerInfo{" +
                "imageUrl='" + imageUrl + '\'' +
                ", link='" + link + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    @Override
    public Object getXBannerUrl() {
        return imageUrl;
    }
}

