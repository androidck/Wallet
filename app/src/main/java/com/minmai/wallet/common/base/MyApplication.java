package com.minmai.wallet.common.base;

import android.content.Context;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.common.helper.ActivityStackManager;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的Application基类
 */
public class MyApplication extends UIApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化吐司工具类
        ToastUtils.init(this);

        // 友盟统计
        // UmengHelper.init(this);

        // Activity 栈管理
        ActivityStackManager.init(this);

        //初始化路由框架
        ARouter.init(MyApplication.this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        //  MultiDex.install(this);
    }
}