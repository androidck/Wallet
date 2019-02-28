package com.minmai.wallet.moudles.request.channel;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.ChannelBank;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChannelPresenter implements ChannelContract.presenter {

    private Context context;
    private ChannelContract.View view;

    public ChannelPresenter(Context context,ChannelContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void queryChannel(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryChannel(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Channel>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<Channel>> t) throws Exception {
                        view.setQueryChannel(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<List<Channel>> t) {
                        if (t.getCode()==100){
                            view.noData();
                        }else {
                            view.fail(t.getMsg());
                        }
                    }
                });
    }

    @Override
    public void queryBankLimit(String creditCardId, String channelId) {
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryBankLimit(creditCardId,channelId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ChannelBank>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<ChannelBank> t) throws Exception {
                        view.setQuota(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<ChannelBank> t) {
                        view.fail(t.getMsg());
                    }
                });
    }
}
