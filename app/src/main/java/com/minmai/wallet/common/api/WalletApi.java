package com.minmai.wallet.common.api;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.enumcode.EnumHttpHeaderParam;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
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
    Observable<BaseEntry<String>> ajaxValidateCode(@Field("codeId") String codeId,@Field("phone") String phone,@Field("code") String code);

    /**
     * 新用户注册
     * @param loginName 手机号
     * @param pwd 密码
     * @param codeId 验证码id
     * @param code 验证码
     * @param recommendCode 推荐码
     * @return
     */
    @FormUrlEncoded
    @POST("user/userRegister")
    Observable<BaseEntry<UserInfo>> userRegister(
                                                 @Field("appId") String appIds,
                                                 @Field("loginName") String loginName,
                                                 @Field("pwd") String pwd,
                                                 @Field("codeId") String codeId,
                                                 @Field("code")String code,
                                                 @Field("recommendCode")String recommendCode);

    /**
     * 密码登录
     * @param loginName 用户名
     * @param pwd 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/userLogin")
    Observable<BaseEntry<UserInfo>> userPwdLogin(
                                                 @Header("X_Timestamp") long currentTimeMillis,
                                                 @Header("X_Signature") String sign,
                                                 @Field("loginName") String loginName,
                                                 @Field("pwd") String pwd);
}
