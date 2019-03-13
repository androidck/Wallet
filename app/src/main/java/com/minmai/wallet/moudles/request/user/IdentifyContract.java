package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.IdentfiyOneReq;
import com.minmai.wallet.moudles.bean.request.QuickPayReq;
import com.minmai.wallet.moudles.bean.request.UserBankCardUpdateReq;
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

        void success(String msg);
    }

    interface presenter extends BasePresenter{
        //查询实名信息
        void queryIdentityAuth(String userId);

        void getDefaultDebitCardVo(String userId);

        void createQuickPay(String user, QuickPayReq quickPayReq);

        //实名认证第一步
        void userRealNameAuthenticationOne(String userId, IdentfiyOneReq identfiyOneReq);

        //实名认证第一步
        void userRealNameAuthenticationTwo(String userId, IdentfiyOneReq identfiyOneReq);

    }
}
