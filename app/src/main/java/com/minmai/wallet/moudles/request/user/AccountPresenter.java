package com.minmai.wallet.moudles.request.user;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class AccountPresenter implements AccountContract.presenter {

    private Context context;
    private AccountContract.View view;

    public AccountPresenter(Context context,AccountContract.View view){
        this.context=context;
        this.view=view;
    }


    /**
     * 设置支付密码
     * @param userId
     * @param paymentPassword
     */
    @Override
    public void addPaymentPassword(String userId, String paymentPassword) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("paymentPassword",paymentPassword);
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().addPaymentPassword(currentTimeMillis,sign,userId,userId,paymentPassword)
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

    /**
     * 修改登录密码
     * @param userId
     * @param oldPwd
     * @param newPwd
     */
    @Override
    public void uPUserPwwd(String userId, String oldPwd, String newPwd) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("oldPwd",oldPwd);
        map.put("newPwd",newPwd);
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().uPUserPwwd(currentTimeMillis,sign,userId,userId,oldPwd,newPwd)
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

    /**
     * 发送短信
     * @param codeUse
     * @param mobile
     */
    @Override
    public void bindSendMsg(String codeUse, String mobile) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().userSendCode(mobile,codeUse)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess(t.getMsg());
                        view.setCodeId(t.getData());
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
     * 忘记密码修改密码
     * @param phone
     * @param code
     * @param codeId
     * @param newPwd
     */
    @Override
    public void forgetUserPwwd(String phone, String code, String codeId, String newPwd) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().forgetUserPwwd(phone,code,codeId,newPwd)
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
