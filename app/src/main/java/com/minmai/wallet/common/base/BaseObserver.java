package com.minmai.wallet.common.base;

import android.accounts.NetworkErrorException;
import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.dialog.ProgressHUD;

import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description: 自定义Observer
 */

public abstract class BaseObserver<T> implements Observer<BaseEntry<T>> {
    protected Context mContext;
    private KProgressHUD progressHUD;
    private String labelTxt;

    public BaseObserver(Context cxt, String text) {
        this.mContext = cxt;
        this.labelTxt = text;
        progressHUD = ProgressHUD.show(mContext);
    }

    //开始
    @Override
    public void onSubscribe(Disposable d) {
        onRequestStart();
    }

    //获取数据
    @Override
    public void onNext(BaseEntry<T> tBaseEntity) {
        try {
            //成功返回成功的数据
            if (tBaseEntity.getCode()==BusinessCode.SUCCESS.getCode()){
                onSuccess(tBaseEntity);
            }else if (tBaseEntity.getCode()==BusinessCode.INCONSISTENT.getCode()){
                //强制下线处理
                mandatoryOffline();
            }else{
                onError(tBaseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //失败
    @Override
    public void onError(Throwable e) {
        onRequestEnd();
        try {
            if (e instanceof ConnectException
                    || e instanceof TimeoutException
                    || e instanceof NetworkErrorException
                    || e instanceof UnknownHostException) {
                onFailure(e, true);  //网络错误
            } else {
                onFailure(e, false);
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

    //结束
    @Override
    public void onComplete() {
        onRequestEnd();//请求结束
    }

    /**
     * 返回成功
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onSuccess(BaseEntry<T> t) throws Exception;


    /**
     * 没有数据或请求失败
     *
     * @param t
     * @throws Exception
     */
    protected abstract void onError(BaseEntry<T> t) throws Exception;


    /**
     * 返回失败
     *
     * @param e
     * @param isNetWorkError 是否是网络错误
     * @throws Exception
     */
    protected abstract void onFailure(Throwable e, boolean isNetWorkError) throws Exception;

    protected void onRequestStart() {
        if (progressHUD != null) {
            progressHUD.setLabel(labelTxt);
        }
    }

    protected void onRequestEnd() {
        if (progressHUD != null) {
            progressHUD.dismiss();
            progressHUD = null;
        }
    }


    //强制下线
    public void mandatoryOffline(){
        //同时清空本地登录数据
        //跳转到登录页面
        ARouter.getInstance().build(ActivityConstant.USER_LOGIN).navigation();
    }

}
