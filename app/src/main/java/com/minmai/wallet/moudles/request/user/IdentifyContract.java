package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.QuickPayReq;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;

/**
 * 查询实名信息接口
 */
public interface IdentifyContract {

    interface View extends BaseView<presenter>{

        void setIdentify(IdentityAuth identify);

        void setDebitCard(DebitCard debitCard);

        void fail(String msg);

        void onSuccess(QuickPayResp quickPayResp);
    }

    interface presenter extends BasePresenter{
        //查询实名信息
        void queryIdentityAuth(String userId);

        void getDefaultDebitCardVo(String userId);

        void createQuickPay(String user, QuickPayReq quickPayReq);
    }
}
