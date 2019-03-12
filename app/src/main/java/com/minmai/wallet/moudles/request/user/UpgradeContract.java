package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.AlipayReq;
import com.minmai.wallet.moudles.bean.response.MemberCentreEntity;

public interface UpgradeContract  {

    interface View extends BaseView<presenter>{
        void success(String msg);

        void fail(String msg);

        void upgradeDate(MemberCentreEntity memberCentreEntity);
    }

    interface presenter extends BasePresenter{
        void getUpgradeDate(String userId);

        //支付宝支付
        void callOrderPay(AlipayReq alipayReq);
    }
}
