package com.minmai.wallet.moudles.request;

import android.content.Context;
import android.util.Log;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.base.BasePresenter;
import com.minmai.wallet.common.base.BaseView;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.moudles.bean.NewsList;
import com.minmai.wallet.moudles.bean.UserInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;

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
                        view.success(t.getMsg(),t.getData());
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

    @Override
    public void userRegisterValidateCode(String codeId, String phone, String code) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().ajaxValidateCode(codeId,phone,code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.success(t.getMsg(),t.getData());
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
