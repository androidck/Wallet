package com.minmai.wallet.moudles.request.card;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.ListBaseData;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreditCardPresenter implements CreditCardContract.presenter {

    private Context context;
    private CreditCardContract.View view;

    public CreditCardPresenter(Context context,CreditCardContract.View view){
        this.context=context;
        this.view=view;
    }

    @Override
    public void queryCreditCard(String userId, ListBaseData listBaseData) {
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(listBaseData), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryCreditCard(currentTimeMillis,sign,userId,listBaseData.getPageCurrent(),listBaseData.getPageSize())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ListBaseData<CreditCard>>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<ListBaseData<CreditCard>> t) throws Exception {
                         view.setCreditCard(t.getData().getList());
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            view.fail("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<ListBaseData<CreditCard>> t) {
                        view.fail(t.getMsg());
                    }
                });
    }


    public static <T> List<T> jsonToDto(String message, String jsonHead,Class<T> cls){ //这里是Class<T>
        JsonObject jsonObject = new JsonParser().parse(message).getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray(jsonHead);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        List<T> list = new ArrayList<>();
        for (JsonElement jsonElement : jsonArray) {
            list.add(gson.fromJson(jsonElement,cls)); //cls
        }
        return list;
    }

}
