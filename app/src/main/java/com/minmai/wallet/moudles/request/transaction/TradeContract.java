package com.minmai.wallet.moudles.request.transaction;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.LeavingMsg;

public interface TradeContract {

    interface View extends BaseView<presenter>{

        void onSetContent(LeavingMsg leavingMsg);

        void fail(String msg);

        void noDate();
    }

    interface presenter extends BasePresenter{

        //查询数据
        void queryTradingRecord(String userId, LeavingMsg leavingMsg);
    }
}
