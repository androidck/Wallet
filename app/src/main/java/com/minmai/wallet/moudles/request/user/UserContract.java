package com.minmai.wallet.moudles.request.user;

import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.moudles.bean.request.UserInfoReq;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;

public interface UserContract  {

    interface View extends BaseView<presenter> {

        void onSetContent(Object object);

        void onSetCodeId(String codeId);

        void onSuccess(String msg);
        //请求失败
        void fail(String msg);

        //设置个人中心资料
        void setPerCenterInfo(PerCenterInfo perCenterInfo);
    }


    interface presenter extends BasePresenter {

        //新用户注册 ---- 发送短信
        void userRegisterSendMsg(String codeUse, String phone);

        //新用户注册 ---- 验证短信
        void userRegisterValidateCode(UserInfoReq userInfoReq);

        //新用户注册 ---- 完成注册
        void userRegister(UserInfoReq infoReq);

        //密码登录
        void userPwdLogin(UserInfoReq userInfoReq);

        //手机发送短信
        void bindSendCode(String mobile,String codeUse);

        //手机短信登录
        void userPhoneLogin(UserInfoReq userInfoReq);

        //获取个人中心资料
        void getUserPerCenterInfo(String userId);

        //获取推荐人信息
        void getRefereeUserInfo(String userId);

        //修改昵称
        void updateUserNiceName(String userId,String nickName);
    }
}
