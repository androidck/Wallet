package com.minmai.wallet.moudles.request.card;

import android.content.Context;

import com.google.gson.Gson;
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.network.OkHttp;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.request.UserBankCardReq;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.CityResp;
import com.minmai.wallet.moudles.bean.response.DistBankCard;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;

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
    public void getBranchBankInfo(String parentId, String cityId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getBranchInfo(currentTimeMillis,sign,parentId,cityId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<CityResp>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<List<CityResp>> t) throws Exception {
                        view.setBranchInfo(t.getData());
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
    public void visBankCard(String cardNo) {

        OkHttp.getAsync(Constant.BANK_CARD_URL+cardNo, new OkHttp.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                DistBankCard distBankCard=new Gson().fromJson(result,DistBankCard.class);
                view.setDisBank(distBankCard);
            }

            @Override
            public void requestFailure(Request request, IOException e) {
                view.fail("网络错误，请联系管理员");
            }
        });
    }

    @Override
    public void userBankCardBinding(String userId, UserBankCardReq userBankCardReq) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(userBankCardReq), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userBankCardBinding(currentTimeMillis,sign,userId,userBankCardReq.getUserId(),userBankCardReq.getCarNumber(),userBankCardReq.getOpenBank(),userBankCardReq.getPhone(),userBankCardReq.getAreaCode(),userBankCardReq.getPhoto(),userBankCardReq.getBankId(),userBankCardReq.getIsDefault())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess("1");
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

    @Override
    public void elementsValidate(String userId,String companyId, String bankcard, String phone) {
        Map<String,String> map=new HashMap<>();
        map.put("companyId",companyId);
        map.put("bankcard",bankcard);
        map.put("phone",phone);
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(map), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().elementsValidate(currentTimeMillis,sign,userId,companyId,bankcard,phone)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.onSuccess(t.getMsg());
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
