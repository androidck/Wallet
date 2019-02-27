package com.minmai.wallet.moudles.request.leave;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.response.ListBaseData;

public interface LeaveContract {

    interface View extends BaseView<presenter>{

        void onContent(ListBaseData leavingMsg);

        void fail(String msg);

        void noDate();
    }

    interface presenter extends BasePresenter{
        //留言列表
        void getListLevMessage(String userId,ListBaseData leavingMsg);
    }
}
