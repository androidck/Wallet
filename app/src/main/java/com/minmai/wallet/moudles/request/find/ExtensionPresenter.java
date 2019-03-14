package com.minmai.wallet.moudles.request.find;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.request.AppSpread;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 *
 */
public class ExtensionPresenter implements ExtensionContract.presenter {

    private Context context;
    private ExtensionContract.View view;

    public ExtensionPresenter(Context context, ExtensionContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取推广页面数据
     * @param userId
     */
    @Override
    public void getAppSpread(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().spread(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<AppSpread>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<AppSpread>> t) throws Exception {
                        view.success(t.getMsg()+t.getCode());
                        view.appSpread(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<AppSpread>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
