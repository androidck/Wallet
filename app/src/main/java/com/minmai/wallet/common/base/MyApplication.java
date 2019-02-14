package com.minmai.wallet.common.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.BuildConfig;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.common.helper.ActivityStackManager;
import com.minmai.wallet.common.uitl.InterceptorUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的Application基类
 */
public class MyApplication extends UIApplication {

    public static final int TIMEOUT = 60;
    private static OkHttpClient mOkHttpClient;
    public static MyApplication myApp;

    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化吐司工具类
        ToastUtils.init(this);

        // 友盟统计
        // UmengHelper.init(this);

        // Activity 栈管理
        ActivityStackManager.init(this);

        myApp=this;
        if (BuildConfig.DEBUG) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);
    }

    /**
     * 全局httpclient
     *
     * @return
     */
    public static OkHttpClient initOKHttp() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                    //cookie

                    .build();
        }
        return mOkHttpClient;
    }
}