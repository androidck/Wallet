package com.minmai.wallet.moudles.request.channel;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.Channel;

import java.util.List;

/**
 * 通道获取接口
 */
public interface ChannelContract {

    interface View extends BaseView<presenter>{

        void setQueryChannel(List<Channel> channel);

        void fail(String msg);

        void noData();
    }

    interface presenter extends BasePresenter{
        //获取通道列表
        void queryChannel(String userId);
    }
}
