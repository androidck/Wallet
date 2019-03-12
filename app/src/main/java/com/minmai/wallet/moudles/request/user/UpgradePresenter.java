package com.minmai.wallet.moudles.request.user;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.request.AlipayReq;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.MemberCentreEntity;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class UpgradePresenter implements UpgradeContract.presenter {

    private Context context;
    private UpgradeContract.View view;

    public UpgradePresenter(Context context,UpgradeContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void getUpgradeDate(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().search(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<MemberCentreEntity>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<MemberCentreEntity> t) throws Exception {
                        view.upgradeDate(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<MemberCentreEntity> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void callOrderPay(AlipayReq alipayReq) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().callOrderPay(alipayReq.getBody(),alipayReq.getSubject(),alipayReq.getOutTradeNo(),alipayReq.getTotalAmount(),alipayReq.getProductCode(),alipayReq.getUserId(),alipayReq.getType(),alipayReq.getMemberLevelId(),alipayReq.getGoodsType())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.success(t.getData());
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
