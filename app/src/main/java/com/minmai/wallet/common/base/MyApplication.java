package com.minmai.wallet.common.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.BuildConfig;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.enumcode.EnumHttpHeaderParam;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.greendao.DaoMaster;
import com.minmai.wallet.common.greendao.DaoSession;
import com.minmai.wallet.common.helper.ActivityStackManager;
import com.minmai.wallet.common.uitl.InterceptorUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的Application基类
 */
public class MyApplication extends UIApplication {

    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    public static Context mContext;

    public static final int TIMEOUT = 60;
    private static OkHttpClient mOkHttpClient;
    public static MyApplication instances;

    public static String JpushId;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        mContext=getApplicationContext();

        instances=this;

        ARouter.init(this); // 尽可能早，推荐在Application中初始化

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志

        JPushInterface.init(this);     		// 初始化 JPush

        // 设置是否开启热更新能力，默认为true
        Beta.enableHotfix = true;
        // 设置是否自动下载补丁
        Beta.canAutoDownloadPatch = true;
        // 设置是否提示用户重启
        Beta.canNotifyUserRestart = true;
        // 设置是否自动合成补丁
        Beta.canAutoPatch = true;

        /*初始化热更新*/
        Bugly.init(getApplicationContext(), "104f0678f2", false);
        // 初始化吐司工具类
        ToastUtils.init(this);
        // Activity 栈管理
        ActivityStackManager.init(this);
        // 初始化数据
        setDatabase();

        //极光推送id
        JpushId=JPushInterface.getRegistrationID(mContext);

    }


    public static MyApplication getInstances(){
        return instances;
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        // 使用 Dex分包
        MultiDex.install(this);

        // 安装tinker
        Beta.installTinker();
    }

    @Override
    public Resources getResources() {//禁止app字体大小跟随系统字体大小调节
        Resources resources = super.getResources();
        if (resources != null && resources.getConfiguration().fontScale != 1.0f) {
            android.content.res.Configuration configuration = resources.getConfiguration();
            configuration.fontScale = 1.0f;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }


    //设置greenDao
    public void setDatabase(){
        // 通过 DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的 SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为 greenDAO已经帮你做了。
        // 注意：默认的 DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }

    /**
     * 全局httpclient
     *
     * @return
     */
    @SuppressLint("NewApi")
    public static OkHttpClient initOKHttp() {
        final long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(2), EnumService.getEnumServiceByServiceName(1))
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(EnumHttpHeaderParam.APPID.getCode()), Constant.APP_ID)//极光推送token
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(EnumHttpHeaderParam.DEVICETOKEN.getCode()), JpushId)//极光推送token
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(EnumHttpHeaderParam.DEVICETYPE.getCode()), "1")
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(EnumHttpHeaderParam.DEVICEIMEL.getCode()), SystemUtil.getInstance().getGenerateIMEI(mContext))
                                    .addHeader(EnumHttpHeaderParam.getHeaderParam(EnumHttpHeaderParam.DEVICEMODEL.getCode()), Build.MODEL)
                                    .build();
                           return chain.proceed(request);
                        }
                    })
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                    .build();
        }
        return mOkHttpClient;
    }
}
