package com.minmai.wallet.moudles.request.user;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.request.UserInfoReq;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.bean.response.RefereeUserInfo;
import com.minmai.wallet.moudles.bean.response.UserInfo;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 用户网络请求
 */
public class UserPresenter implements UserContract.presenter{


    private Context context;
    private UserContract.View view;

    public UserPresenter(Context context,UserContract.View view){
        this.context=context;
        this.view=view;
    }

    /**
     * 注册发送短信
     * @param codeUse
     * @param phone
     */
    @Override
    public void userRegisterSendMsg(String codeUse, String phone) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().userRegisterSendMsg(codeUse,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                         view.onSuccess(t.getMsg());
                         view.onSetCodeId(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<String> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 注册验证短信
     */
    @Override
    public void userRegisterValidateCode(UserInfoReq userInfoReq) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(userInfoReq),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().ajaxValidateCode(currentTimeMillis,sign,userInfoReq.getCodeId(),userInfoReq.getPhone(),userInfoReq.getCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.onSetCodeId(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<String> t) {
                        view.fail(t.getMsg());
                    }
                });
    }


    /**
     * 新用户注册
     * @param infoReq
     */
    @Override
    public void userRegister(UserInfoReq infoReq) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(infoReq),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userRegister(currentTimeMillis,sign,Constant.APP_ID,infoReq.getLoginName(),infoReq.getPwd(),infoReq.getCodeId(),infoReq.getCode(),infoReq.getRecommendCode())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserInfo>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<UserInfo> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.onSetContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<UserInfo> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 密码登录
     * @param userInfoResp
     */
    @Override
    public void userPwdLogin(UserInfoReq userInfoResp) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(userInfoResp),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userPwdLogin(currentTimeMillis,sign, userInfoResp.getLoginName(), userInfoResp.getPwd())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserInfo>(context,MainUtil.loadLogin) {
                    @Override
                    protected void onSuccess(BaseEntry<UserInfo> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.onSetContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<UserInfo> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 手机发送验证码
     * @param mobile
     * @param codeUse
     */
    @Override
    public void bindSendCode(String mobile, String codeUse) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().userSendCode(mobile,codeUse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.onSetCodeId(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<String> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 手机短信登录
     * @param userInfoReq
     */
    @Override
    public void userPhoneLogin(UserInfoReq userInfoReq) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(userInfoReq),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().phoneUserLogin(currentTimeMillis,sign, userInfoReq.getPhone(),userInfoReq.getCode(),userInfoReq.getCodeId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserInfo>(context,MainUtil.loadLogin) {
                    @Override
                    protected void onSuccess(BaseEntry<UserInfo> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.onSetContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<UserInfo> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 获取个人中心资料
     * @param userId
     */
    @Override
    public void getUserPerCenterInfo(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getUserPersonalCenter(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<PerCenterInfo>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<PerCenterInfo> t) throws Exception {
                        view.setPerCenterInfo(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<PerCenterInfo> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 获取用户id
     * @param userId
     */
    @Override
    public void getRefereeUserInfo(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getRefereeUserInfo(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RefereeUserInfo>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<RefereeUserInfo> t) throws Exception {
                       view.onSetContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<RefereeUserInfo> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 修改昵称
     * @param userId
     * @param nickName
     */
    @Override
    public void updateUserNiceName(String userId, String nickName) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        Map<String,String> map=new HashMap<>();
        map.put("nickName",nickName);
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().updateUserNiceName(currentTimeMillis,sign,userId,nickName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess(t.getMsg());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<String> t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
