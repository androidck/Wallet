package com.minmai.wallet.moudles.request.banner;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.RollMessage;

import java.util.List;

/**
 * 轮播图接口
 */
public interface BannerContract {

    interface View extends BaseView<presenter>{


        //请求成功
        void setContent(List<BannerInfo> list);

        //请求失败
        void fail(String msg);

        void setRollMessage(List<RollMessage> list);
    }

    interface presenter extends BasePresenter{

        //获取轮播图列表
        void getBannerList();

        //获取轮播消息
        void getRollMessageList();
    }
}
