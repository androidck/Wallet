package com.minmai.wallet.common.api;

import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.moudles.bean.request.AppSpread;
import com.minmai.wallet.moudles.bean.response.BankInfo;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.ChannelBank;
import com.minmai.wallet.moudles.bean.response.CityResp;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.DividedOrWithdrawalDetailsList;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.ListLeaving;
import com.minmai.wallet.moudles.bean.response.MemberCentreEntity;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;
import com.minmai.wallet.moudles.bean.response.RefereeUserInfo;
import com.minmai.wallet.moudles.bean.response.RegisterStateResp;
import com.minmai.wallet.moudles.bean.response.RollMessage;
import com.minmai.wallet.moudles.bean.response.Trade;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;
import com.minmai.wallet.moudles.bean.response.UserInfo;
import com.minmai.wallet.moudles.db.BankBackGround;

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
    Observable<BaseEntry<ListBaseData<ListLeaving>>>listLevMessage(
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
     * 储蓄卡列表
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param pageCurrent
     * @param pageSize
     * @return
     */
    @POST("user/queryDebitCard")
    @FormUrlEncoded
    Observable<BaseEntry<ListBaseData<DebitCard>>>queryDebitCard(
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



    @POST("user/userRealNameAuthenticationTwo")
    @FormUrlEncoded
    Observable<BaseEntry<String>>userRealNameAuthenticationTwo(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId,
            @Field("userId") String userId,
            @Field("handIdCard") String handIdCard);

    /**
     * 获取总行信息
     * @return
     */
    @POST("user/getBankInfoVo")
    Observable<BaseEntry<List<BankInfo>>>getBankInfo(
            @Header("X_Timestamp") long currentTimeMillis,
            @Header("X_Signature") String sign,
            @Header("X_UserId") String xUserId);

    /**
     * 获取支行数据
     * @return
     */
    @POST("user/bankBranch")
    @FormUrlEncoded
    Observable<BaseEntry<List<CityResp>>>getBranchInfo(@Header("X_Timestamp") long currentTimeMillis,
                                                       @Header("X_Signature") String sign,
                                                       @Field("parentId") String parentId,
                                                       @Field("cityId") String cityId);


    /**
     * 完善信息第三部
     * @param currentTimeMillis
     * @param sign
     * @return
     */


    @POST("user/userBankCardBinding")
    @FormUrlEncoded
    Observable<BaseEntry<String>>userBankCardBinding(@Header("X_Timestamp") long currentTimeMillis,
                                                        @Header("X_Signature") String sign,
                                                        @Header("X_UserId") String xUserId,
                                                        @Field("userId") String userId,
                                                        @Field("carNumber") String carNumber,
                                                        @Field("openBank") String openBank,
                                                        @Field("phone") String phone,
                                                        @Field("areaCode") String areaCode,
                                                        @Field("photo") String photo,
                                                        @Field("bankId") String bankId,
                                                        @Field("isDefault") String isDefault
                                                );
    /**
     * 四要素验证
     *
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param companyId
     * @param carNumber
     * @param phone
     * @return
     */
    @POST("element/elementsValidate")
    @FormUrlEncoded
    Observable<BaseEntry<String>>elementsValidate(@Header("X_Timestamp") long currentTimeMillis,
                                                     @Header("X_Signature") String sign,
                                                     @Header("X_UserId") String xUserId,
                                                     @Field("companyId") String companyId,
                                                     @Field("bankcard") String carNumber,
                                                     @Field("phone") String phone);


    /**
     * 获取用户进件状态
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("user/queryRegisterState")
    Observable<BaseEntry<RegisterStateResp>>queryRegisterState(@Header("X_Timestamp") long currentTimeMillis,
                                                               @Header("X_Signature") String sign,
                                                               @Header("X_UserId") String xUserId);


    /**
     * 修改信用卡昵称
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param creditId
     * @param creditAlias
     * @return
     */
    @POST("trade/updateCreditAlias")
    @FormUrlEncoded
    Observable<BaseEntry<String>>updateCreditAlias(@Header("X_Timestamp") long currentTimeMillis,
                                                              @Header("X_Signature") String sign,
                                                              @Header("X_UserId") String xUserId,
                                                              @Field("id") String creditId,
                                                              @Field("creditAlias") String creditAlias);

    /**
     * 添加信用卡
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param creditId
     * @return
     */
    @POST("trade/addCreditCard")
    @FormUrlEncoded
    Observable<BaseEntry<String>>addCreditCard(@Header("X_Timestamp") long currentTimeMillis,
                                                   @Header("X_Signature") String sign,
                                                   @Header("X_UserId") String xUserId,
                                                   @Field("carNumber") String creditId,
                                                   @Field("phone") String phone,
                                                   @Field("cvv") String cvv,
                                                   @Field("branch_bank") String branchBank,
                                                   @Field("effectiveDate") String effectiveDate,
                                                   @Field("photo") String photo,
                                                   @Field("nickName") String nickName,
                                                   @Field("statementDate") String statementDate,
                                                   @Field("repaymentDate") String repaymentDate);


    /**
     * 获取银行卡背景颜色
     * @param repaymentDate
     * @return
     */
    @POST("user/getBankBackgroundVo")
    @FormUrlEncoded
    Observable<BaseEntry<BankBackGround>>getBankBackgroundVo(@Field("bankCarNum") String repaymentDate);

    /**
     * 阅读留言
     * @param id
     * @return
     */
    @POST("user/updateReadState")
    @FormUrlEncoded
    Observable<BaseEntry<String>>updateReadState(@Field("id") String id);


    /**
     * 信用卡解绑
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param creditId
     * @return
     */
    @POST("trade/delCreditCard")
    @FormUrlEncoded
    Observable<BaseEntry<String>>delCreditCard(@Header("X_Timestamp") long currentTimeMillis,
                                                   @Header("X_Signature") String sign,
                                                   @Header("X_UserId") String xUserId,
                                                   @Field("id") String creditId);

    /**
     * 查询升级页面信息
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("upgrade/search")
    Observable<BaseEntry<MemberCentreEntity>>search(@Header("X_Timestamp") long currentTimeMillis,
                                                    @Header("X_Signature") String sign,
                                                    @Header("X_UserId") String xUserId);


    /**
     * 支付宝支付
     * @param body
     * @param subject
     * @param outTradeNo
     * @param totalAmount
     * @param productCode
     * @param userId
     * @param type
     * @param memberLevelId
     * @param goodsType
     * @return
     */
    @POST("alipay/callOrderPay")
    @FormUrlEncoded
    Observable<BaseEntry<String>>callOrderPay(@Field("body") String body,
                                                          @Field("subject")String subject,
                                                          @Field("outTradeNo")String outTradeNo,
                                                          @Field("totalAmount")String totalAmount,
                                                          @Field("productCode") String productCode,
                                                          @Field("userId")String userId,
                                                          @Field("type")String type,
                                                          @Field("memberLevelId")String memberLevelId,
                                                          @Field("goodsType")String goodsType);

    /**
     * 我的分润
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("record/listForRecords")
    @FormUrlEncoded
    Observable<BaseEntry<ListBaseData<DividedOrWithdrawalDetailsList>>>listForRecords(@Header("X_Timestamp") long currentTimeMillis,
                                                                                      @Header("X_Signature") String sign,
                                                                                      @Header("X_UserId") String xUserId,
                                                                                      @Field("pageCurrent") int pageCurrent,
                                                                                      @Field("pageSize") int pageSize
                                                );


    /**
     * 修改默认储蓄卡
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @param userId
     * @param carNumber
     * @param openBank
     * @param phone
     * @param areaCode
     * @param photo
     * @param bankId
     * @param isDefault
     * @return
     */
    @POST("user/updateUserBankCard")
    @FormUrlEncoded
    Observable<BaseEntry<String>>modifyDefaultDebitCard(@Header("X_Timestamp") long currentTimeMillis,
                                                     @Header("X_Signature") String sign,
                                                     @Header("X_UserId") String xUserId,
                                                     @Field("oldDebitCardId") String oldDebitCardId,
                                                     @Field("userId") String userId,
                                                     @Field("carNumber") String carNumber,
                                                     @Field("openBank") String openBank,
                                                     @Field("phone") String phone,
                                                     @Field("areaCode") String areaCode,
                                                     @Field("photo") String photo,
                                                     @Field("bankId") String bankId,
                                                     @Field("isDefault") String isDefault
    );


    /**
     * 退出登录
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("user/userSignOut")
    Observable<BaseEntry<String>>userSignOut(@Header("X_Timestamp") long currentTimeMillis,
                                             @Header("X_Signature") String sign,
                                             @Header("X_UserId") String xUserId);

    /**
     * 获取推广页面数据
     * @param currentTimeMillis
     * @param sign
     * @param xUserId
     * @return
     */
    @POST("spread/search")
    Observable<BaseEntry<List<AppSpread>>>spread(@Header("X_Timestamp") long currentTimeMillis,
                                                 @Header("X_Signature") String sign,
                                                 @Header("X_UserId") String xUserId);



}

