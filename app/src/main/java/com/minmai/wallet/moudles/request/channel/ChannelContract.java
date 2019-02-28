package com.minmai.wallet.moudles.request.channel;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.ChannelBank;

import java.util.List;

/**
 * 通道获取接口
 */
public interface ChannelContract {

    interface View extends BaseView<presenter>{

        void setQueryChannel(List<Channel> channel);

        void fail(String msg);

        void setQuota(ChannelBank channelBank);

        void noData();
    }

    interface presenter extends BasePresenter{
        //获取通道列表
        void queryChannel(String userId);

        //查询银行卡限额
        void queryBankLimit(String creditCardId,String channelId);
    }
}
