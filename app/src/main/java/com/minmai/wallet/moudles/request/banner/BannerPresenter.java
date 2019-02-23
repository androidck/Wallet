package com.minmai.wallet.moudles.request.banner;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.moudles.bean.response.BannerInfo;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BannerPresenter implements BannerContract.presenter {

    private Context context;
    private BannerContract.View view;

    public BannerPresenter(Context context,BannerContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void getBannerList() {
        RetrofitUtil
                .getInstance()
                .initRetrofit().getBannerList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BannerInfo>>(context, MainUtil.loadLogin) {
                    @Override
                    protected void onSuccess(BaseEntry<List<BannerInfo>>t) throws Exception {
                        view.setContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry <List<BannerInfo>>t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
