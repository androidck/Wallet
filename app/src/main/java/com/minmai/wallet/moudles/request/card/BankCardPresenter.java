package com.minmai.wallet.moudles.request.card;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BankCardPresenter implements BankCardContract.presenter {

    private Context context;
    private BankCardContract.View view;

    public BankCardPresenter(Context context,BankCardContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void getBankInfoVo(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getBankInfo(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<BankInfo>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<BankInfo>> t) throws Exception {
                        view.setBankInfo(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<BankInfo>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void getProvince(String parentId) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().getCityInfo(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CityResp>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<CityResp>> t) throws Exception {
                       view.setProvince(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<CityResp>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void getCity(String parentId) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().getCityInfo(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CityResp>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<CityResp>> t) throws Exception {
                        view.setCity(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<CityResp>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void getArea(String parentId) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().getCityInfo(parentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CityResp>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<CityResp>> t) throws Exception {
                        view.setArea(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<CityResp>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
