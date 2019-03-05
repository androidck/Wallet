package com.minmai.wallet.moudles.request.user;

import android.content.Context;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.request.IdentfiyOneReq;
import com.minmai.wallet.moudles.bean.request.QuickPayReq;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Field;

public class IdentifyPresenter implements IdentifyContract.presenter {

    private Context context;
    private IdentifyContract.View view;

    public IdentifyPresenter(Context context,IdentifyContract.View view){
        this.context=context;
        this.view=view;
    }

    /**
     * 查询实名信息
     * @param userId
     */
    @Override
    public void queryIdentityAuth(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryIdentityAuth(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<IdentityAuth>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<IdentityAuth> t) throws Exception {
                        view.setIdentify(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<IdentityAuth> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void getDefaultDebitCardVo(String userId) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().getDefaultDebitCardVo(currentTimeMillis,sign,userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<DebitCard>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<DebitCard> t) throws Exception {
                        view.setDebitCard(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<DebitCard> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    @Override
    public void createQuickPay(String userId, QuickPayReq quickPayReq) {
        Map<String,String> map=new HashMap<>();


        map.put("channelId",quickPayReq.getChannelId());
        map.put("money",quickPayReq.getMoney());
        map.put("debitCardId",quickPayReq.getDebitCardId());
        map.put("creditCardId",quickPayReq.getCreditCardId());
        map.put("returnUrl",quickPayReq.getReturnUrl());
        map.put("city",quickPayReq.getCity());
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().createQuickPay(currentTimeMillis,sign,userId,quickPayReq.getChannelId(),quickPayReq.getMoney(),quickPayReq.getDebitCardId(),quickPayReq.getCreditCardId(),quickPayReq.getReturnUrl(),quickPayReq.getCity())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<QuickPayResp>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<QuickPayResp> t) throws Exception {
                        view.onSuccess(t.getData());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<QuickPayResp> t) {
                        view.fail(t.getMsg());
                    }
                });
    }

    /**
     * 完善资料第一步
     * @param userId
     * @param identfiyOneReq
     */
    @Override
    public void userRealNameAuthenticationOne(String userId, IdentfiyOneReq identfiyOneReq) {

        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(identfiyOneReq),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userRealNameAuthenticationOne(currentTimeMillis,sign,userId,userId,identfiyOneReq.getRealName(),identfiyOneReq.getIdCard(),identfiyOneReq.getEffectiveDate(),identfiyOneReq.getCardFrontPic(),identfiyOneReq.getCardBackPic(),identfiyOneReq.getNation(),identfiyOneReq.getDetailedAddress())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.success(t.getMsg());
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
    public void userRealNameAuthenticationTwo(String userId, IdentfiyOneReq identfiyOneReq) {
        Map<String,String> map=new HashMap<>();
        map.put("userId",userId);
        map.put("handIdCard",identfiyOneReq.getHandIdCard());
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userRealNameAuthenticationTwo(currentTimeMillis,sign,userId,userId,identfiyOneReq.getHandIdCard())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        view.success(t.getMsg());
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
