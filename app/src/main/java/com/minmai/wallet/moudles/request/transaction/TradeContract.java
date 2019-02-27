package com.minmai.wallet.moudles.request.transaction;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.ListBaseData;

public interface TradeContract {

    interface View extends BaseView<presenter>{

        void onSetContent(ListBaseData leavingMsg);

        void fail(String msg);

        void noDate();
    }

    interface presenter extends BasePresenter{

        //查询数据
        void queryTradingRecord(String userId, ListBaseData leavingMsg);
    }
}
