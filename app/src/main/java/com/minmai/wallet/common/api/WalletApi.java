package com.minmai.wallet.common.api;

import com.minmai.wallet.common.base.BaseEntry;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 请求Api
 */
public interface WalletApi {

    /**
     * 注册发送验证码
     * @param codeUse 场景
     * @param phone 手机号
     * @return
     */
    @FormUrlEncoded
    @POST("code/bindSendCode")
    Observable<BaseEntry<String>> userRegisterSendMsg(@Field("codeUse") String codeUse,@Field("mobile") String phone);

    /**
     * 验证验证码
     * @param phone
     * @return
     */
    @FormUrlEncoded
    @POST("code/ajaxValidateCode")
    Observable<BaseEntry<String>> ajaxValidateCode(@Field("codeId") String codeId,@Field("mobile") String phone,@Field("code") String code);
}
