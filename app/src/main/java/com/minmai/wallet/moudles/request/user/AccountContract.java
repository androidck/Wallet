package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;

import retrofit2.http.Field;

/**
 * 账户安全
 */
public interface AccountContract {

    interface View extends BaseView<presenter>{

        void onSuccess(String msg);

        void fail(String msg);

        void setCodeId(String codeId);
    }

    interface presenter extends BasePresenter{

        //修改支付密码
        void addPaymentPassword(String userId,String paymentPassword);

        //修改登录密码
        void uPUserPwwd(String userId,String oldPwd,String newPwd);

        //发送验证吗
        void bindSendMsg(String codeUse,String mobile);

        //忘记密码修改密码
        void forgetUserPwwd(String phone,String code, String codeId,String newPwd);
    }
}
