package com.minmai.wallet.moudles.request.transaction;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.Trade;

public interface TradeContract {

    interface View extends BaseView<presenter>{

        void onSetContent(ListBaseData<Trade> leavingMsg);

        void fail(String msg);

        void noDate();
    }

    interface presenter extends BasePresenter{

        //查询数据
        void queryTradingRecord(String userId, ListBaseData leavingMsg);
    }
}
