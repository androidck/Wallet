package com.minmai.wallet.common.api;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseResponse;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.ChannelBank;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;
import com.minmai.wallet.moudles.bean.response.RefereeUserInfo;
import com.minmai.wallet.moudles.bean.response.RollMessage;
import com.minmai.wallet.moudles.bean.response.Trade;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;
import com.minmai.wallet.moudles.bean.response.UserInfo;

import java.util.List;

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

    /***
     * 首页轮播图
     * @return
     */
    @POST("banner/queryBanner")
    Observable<BaseEntry<List<BannerInfo>>> getBannerList();

    /**
     * 轮播
     * @return
     */
    @POST("trade/queryAppRollMessage")
    Observable<BaseEntry<List<RollMessage>>> getRollMessageList();

    /**
     * 发现页面查询分润
     * @param userId
     * @return
     */
    @POST("found/getFoundPageAllInitInfo")
    @FormUrlEncoded
    Observable<BaseEntry<UserGounpCount>>getFoundPageAllInitInfo(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Field("userId") String userId);


    /**
     * 查询个人资料
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @return
     */
    @POST("user/getUserPersonalCenter")
    Observable<BaseEntry<PerCenterInfo>>getUserPersonalCenter(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId);


    /**
     * 获取推荐人信息
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @return
     */
    @POST("user/getRefereeUserInfo")
    Observable<BaseEntry<RefereeUserInfo>>getRefereeUserInfo(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId);

    /**
     * 给推荐人留言
     * @return
     */
    @POST("user/userLeaveMessage")
    @FormUrlEncoded
    Observable<BaseEntry<String>>userLeaveMessage(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("refereeId") String refereeId,
            @Field("leaveMessageContent") String leaveMessageContent);

    /**
     * 修改用户昵称
     * @return
     */
    @POST("user/updateUserNiceName")
    @FormUrlEncoded
    Observable<BaseEntry<String>>updateUserNiceName(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("nickName") String nickName);

    /**
     * 修改用户昵称
     * @return
     */
    @POST("user/listLevMessage")
    @FormUrlEncoded
    Observable<BaseEntry<ListBaseData>>listLevMessage(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("pageCurrent") int pageCurrent,
            @Field("pageSize") int pageSize);


    /**
     * 查询交易记录
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @POST("trade/queryTradingRecord")
    @FormUrlEncoded
    Observable<BaseEntry<ListBaseData<Trade>>>queryTradingRecord(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("pageCurrent") int pageCurrent,
            @Field("pageSize") int pageSize);


    /**
     * 用户反馈
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @param feedbackContent
     * @return
     */
    @POST("user/userFeedback")
    @FormUrlEncoded
    Observable<BaseEntry<String>>userFeedback(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("feedbackContent") String feedbackContent);

    /**
     * 是否允许下级查看手机号
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @param extendOne
     * @return
     */
    @POST("user/updateExtendOne")
    @FormUrlEncoded
    Observable<BaseEntry<String>>updateExtendOne(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String userId,
            @Field("extendOne") String extendOne);

    /**
     * 设置支付密码
     * @param currentTimeMillis
     * @param sign
     * @param userId
     * @param paymentPassword
     * @return
     */
    @POST("user/addPaymentPassword")
    @FormUrlEncoded
    Observable<BaseEntry<String>>addPaymentPassword(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("userId") String userId,
            @Field("paymentPassword") String paymentPassword);

    /**
     * 修改登录密码
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param userId
     * @return
     */
    @POST("user/uPUserPwwd")
    @FormUrlEncoded
    Observable<BaseEntry<String>>uPUserPwwd(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("userId") String userId,
            @Field("oldPwd") String oldPwd,
            @Field("newPwd") String newPwd);

    /**
     * 忘记密码修改密码
     * @param userId
     * @param code
     * @param codeId
     * @param newPwd
     * @return
     */
    @POST("user/forgetUserPwwd")
    @FormUrlEncoded
    Observable<BaseEntry<String>>forgetUserPwwd(
            @Field("phone") String userId,
            @Field("code") String code,
            @Field("codeId") String codeId,
            @Field("newPwd") String newPwd);



    /**
     * 获取通道列表
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("trade/queryChannel")
    Observable<BaseEntry<List<Channel>>>queryChannel(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId);

    /**
     * 信用卡列表
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @POST("trade/queryCreditCard")
    @FormUrlEncoded
    Observable<BaseEntry<ListBaseData<CreditCard>>>queryCreditCard(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("pageCurrent") int pageCurrent,
            @Field("pageSize") int pageSize);


    /**
     * 查询交易上线
     * @param creditCardId
     * @param channelId
     * @return
     */
    @POST("trade/queryBankLimit")
    @FormUrlEncoded
    Observable<BaseEntry<ChannelBank>>queryBankLimit(
            @Field("creditCardId") String creditCardId,
            @Field("channelId") String channelId);


    /**
     * 查询实名信息
     * @param xUserId
     * @return
     */
    @POST("user/queryIdentityAuth")
    Observable<BaseEntry<IdentityAuth>>queryIdentityAuth(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId);

    /**
     * 查询默认储蓄卡
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("user/getDefaultDebitCardVo")
    Observable<BaseEntry<DebitCard>>getDefaultDebitCardVo(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId);

    /**
     * 刷卡交易
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param channelId
     * @param money
     * @param debitCardId
     * @param creditCardId
     * @return
     */
    @POST("trade/createQuickpay")
    @FormUrlEncoded
    Observable<BaseEntry<QuickPayResp>>createQuickPay(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("channelId") String channelId,
            @Field("money") String money,
            @Field("debitCardId") String debitCardId,
            @Field("creditCardId") String creditCardId,
            @Field("returnUrl") String returnUrl,
            @Field("city") String city);

    /**
     * 完善信息第一步
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param userId
     * @param realName
     * @param idCard
     * @param effectiveDate
     * @param cardFrontPic
     * @param cardBackPic
     * @param nation
     * @param detailedAddress
     * @return
     */
    @POST("user/userRealNameAuthenticationOne")
    @FormUrlEncoded
    Observable<BaseEntry<String>>userRealNameAuthenticationOne(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("userId") String userId,
            @Field("realName") String realName,
            @Field("idCard") String idCard,
            @Field("effectiveDate") String effectiveDate,
            @Field("cardFrontPic") String cardFrontPic,
            @Field("cardBackPic") String cardBackPic,
            @Field("nation") String nation,
            @Field("detailedAddress") String detailedAddress);

}
