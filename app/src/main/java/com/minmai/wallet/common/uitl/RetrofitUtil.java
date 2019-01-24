package com.minmai.wallet.common.uitl;



import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author: Allen.
 * @date: 2018/7/25
 * @description: retrofit请求工具类
 */

public class RetrofitUtil {
    /**
     * 超时时间
     */
    private static volatile RetrofitUtil mInstance;

/*    private MainApi mainApi;*/

    /**
     * 单例封装
     *
     * @return
     */
    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }

   /**
     * 初始化Retrofit
     */
/*    public MainApi initRetrofit() {
        if (mainApi == null) {
            Retrofit mRetrofit = new Retrofit.Builder()
                    .client(MyApplication.initOKHttp())
                    // 设置请求的域名
                    .baseUrl(Constant.NEWS_URL)
                    // 设置解析转换工厂，用自己定义的
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
            mainApi = mRetrofit.create(MainApi.class);
        }
        return mainApi;
    }*/

    /**
     * 初始化Retrofit
     */

}
