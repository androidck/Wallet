package com.minmai.wallet.moudles.request.find;

import android.content.Context;
import android.util.Log;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FoundPagePresenter {

    private Context context;
    private FoundPageContract.View view;

    public FoundPagePresenter(Context context, FoundPageContract.View view) {
        this.context = context;
        this.view = view;
    }

    /**
     * 获取发现页数据
     * @param userId
     */
    public void getFoundPageAllInitInfo(String userId){
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getFoundPageAllInitInfo(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<UserGounpCount>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<UserGounpCount> t) throws Exception {
                        view.setContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<UserGounpCount> t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
