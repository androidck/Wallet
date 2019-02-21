package com.minmai.wallet.moudles.request;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.UserInfo;

public interface UserContract  {

    interface View extends BaseView<presenter> {
        //设置内容
        void setContent(UserInfo userInfo);

        //请求成功
        void success(String msg,Object object);

        //请求失败
        void fail(String msg);
    }


    interface presenter extends BasePresenter {

        //新用户注册 ---- 发送短信
        void userRegisterSendMsg(String codeUse, String phone);

        //新用户注册 ---- 验证短信
        void userRegisterValidateCode(String codeId, String phone,String code);
    }
}
