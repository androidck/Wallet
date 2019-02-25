package com.minmai.wallet.moudles.request.leave;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.LeavingMsg;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LeavePresenter implements LeaveContract.presenter{

    private Context context;
    private LeaveContract.View view;

    public LeavePresenter(Context context,LeaveContract.View view){
        this.context=context;
        this.view=view;
    }

    //获取列表
    public void getListLevMessage(String userId,LeavingMsg leavingMsg){
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(leavingMsg),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().listLevMessage(currentTimeMillis,sign,userId,leavingMsg.getPageCurrent(),leavingMsg.getPageSize())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<LeavingMsg>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<LeavingMsg> t) throws Exception {
                        view.onContent(t.getData());
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
                            view.noDate(t.getCode());
                        }else {
                            view.fail(t.getMsg());
                        }
                    }
                });
    }
}
