package com.minmai.wallet.moudles.request.transaction;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.LeavingMsg;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class TradePresenter implements TradeContract.presenter{

    public Context context;
    private TradeContract.View view;

    public TradePresenter(Context context,TradeContract.View view){
        this.context=context;
        this.view=view;
    }

    //交易记录
    @Override
    public void queryTradingRecord(String userId, LeavingMsg leavingMsg) {
        Map<String,String> map=new HashMap<>();
        map.put("pageCurrent",leavingMsg.getPageCurrent()+"");
        map.put("pageSize",leavingMsg.getPageSize()+"");
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryTradingRecord(currentTimeMillis,sign,userId,leavingMsg.getPageCurrent(),leavingMsg.getPageSize())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LeavingMsg>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<LeavingMsg> t) throws Exception {
                        view.onSetContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<LeavingMsg> t) {
                        if (t.getCode()==100){
                            view.noDate();
                        }else {
                            view.fail(t.getMsg());
                        }
                    }
                });
    }
}
