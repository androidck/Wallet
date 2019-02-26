package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;

/**
 * 意见反馈
 */
public interface FeedBackContract {

    interface View extends BaseView<present>{

        void onSuccess(String msg);

        void fail(String msg);
    }

    interface present extends BasePresenter{

        //用户反馈
        void userFeedBack(String userId,String feedbackContent);
    }
}
