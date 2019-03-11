package com.minmai.wallet.moudles.request.leave;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.ListLeaving;

import java.util.HashMap;
import java.util.Map;

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
    public void getListLevMessage(String userId,ListBaseData leavingMsg){
        Map<String,String> map=new HashMap<>();
        map.put("pageCurrent",leavingMsg.getPageCurrent()+"");
        map.put("pageSize",leavingMsg.getPageSize()+"");
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().listLevMessage(currentTimeMillis,sign,userId,leavingMsg.getPageCurrent(),leavingMsg.getPageSize())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ListBaseData<ListLeaving>>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<ListBaseData<ListLeaving>> t) throws Exception {
                        view.onContent(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<ListBaseData<ListLeaving>> t) {
                        if (t.getCode()==100){
                            view.noDate();
                        }else {
                            view.fail(t.getMsg());
                        }
                    }
                });
    }
}
