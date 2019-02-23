package com.minmai.wallet.common.api;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.moudles.bean.response.UserInfo;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

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
    Observable<BaseEntry<String>> ajaxValidateCode( @Header("X_Timestamp") long currentTimeMillis,
                                                     @Header("X_Signature") String sign,
                                                     @Field("codeId") String codeId,
                                                     @Field("phone") String phone,
                                                     @Field("code") String code);

    /**
     * 手机发送验证码
     * @param mobile 手机号
     * @param codeUse 场景值
     * @return
     */
    @FormUrlEncoded
    @POST("code/userSendCode")
    Observable<BaseEntry<String>> userSendCode(@Field("mobile")String mobile,
                                               @Field("codeUse")String codeUse);
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
                                                 @Header("X_Timestamp") long currentTimeMillis,
                                                 @Header("X_Signature") String sign,
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


    /**
     * 验证码登录
     * @param pwd 密码
     * @return
     */
    @FormUrlEncoded
    @POST("user/phoneUserLogin")
    Observable<BaseEntry<UserInfo>> phoneUserLogin(
                                        @Header("X_Timestamp") long currentTimeMillis,
                                        @Header("X_Signature") String sign,
                                        @Field("phone") String phone,
                                        @Field("code") String pwd,
                                        @Field("codeId") String codeId
    );

}
