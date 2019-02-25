package com.minmai.wallet.moudles.request.referee;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.LeaveMessageReq;

public interface RefereeContract {

    interface View extends BaseView<presenter>{

        void onSuccess(String msg);

        void fail(String msg);
    }

    interface presenter extends BasePresenter{

        //给推荐人留言
        void userLeaveMessage(String userId, LeaveMessageReq leaveMessageReq);
    }
}
