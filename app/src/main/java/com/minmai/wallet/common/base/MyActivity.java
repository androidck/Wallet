package com.minmai.wallet.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.google.gson.Gson;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.hjq.toast.ToastUtils;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.dialog.ProgressHUD;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.greendao.DbBankInfoDao;
import com.minmai.wallet.common.greendao.DbCenterInfoDao;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.greendao.RegisterStateRespDao;
import com.minmai.wallet.common.network.OkHttp;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.RegisterStateResp;
import com.minmai.wallet.moudles.bean.response.ValideCard;
import com.minmai.wallet.moudles.db.DbBankInfo;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.ui.identity.IdentifyOneActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyThreeActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyTwoActivity;
import com.minmai.wallet.moudles.ui.main.MainActivity;
import com.minmai.wallet.moudles.web.BrowserActivity;
import com.minmai.wallet.moudles.web.SonicJavaScriptInterface;

import java.io.IOException;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Request;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends UIActivity
        implements OnTitleBarListener {

    private Unbinder mButterKnife;//View注解

    public KProgressHUD progressHUD;

    public static DbUserInfoDao userInfoDao;
    public static DbCenterInfoDao centerInfoDao;
    public static DbBankInfoDao bankInfoDao;
    public static RegisterStateRespDao respDao;
    public Context context;

    public int currentPage=1;
    public int pageSize=10;

    @Override
    protected void initLayout() {
        super.initLayout();

        //初始化标题栏的监听
        if (getTitleBarId() > 0) {
            if (findViewById(getTitleBarId()) instanceof TitleBar) {
                ((TitleBar) findViewById(getTitleBarId())).setOnTitleBarListener(this);
            }
        }

        mButterKnife = ButterKnife.bind(this);
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        centerInfoDao=MyApplication.getInstances().getDaoSession().getDbCenterInfoDao();
        bankInfoDao=MyApplication.getInstances().getDaoSession().getDbBankInfoDao();
        respDao=MyApplication.getInstances().getDaoSession().getRegisterStateRespDao();
        context=this;

        initOrientation();
    }

    /**
     * 初始化横竖屏方向，会和 LauncherTheme 主题样式有冲突，注意不要同时使用
     */
    protected void initOrientation() {
        // 当前 Activity 不能是透明的并且没有指定屏幕方向，默认设置为竖屏
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    protected void startRequestInterface() {

    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(int titleId) {
        setTitle(getText(titleId));
    }

    /**
     * 设置标题栏的标题
     */
    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        TitleBar titleBar = getTitleBar();
        if (titleBar != null) {
            titleBar.setTitle(title);
        }
    }



    /**
     * 获取userId
     * @return
     */
    public static String getUserId(){
        return userInfoDao.loadAll().get(0).getUserId();
    }

    /**
     * 获取手机号
     * @return
     */
    public static String getPhone(){
        return userInfoDao.loadAll().get(0).getPhone();
    }

    //是否展示手机号
    public static String isShowPhone(){
        return centerInfoDao.loadAll().get(0).getExtendOne();
    }


    //获取总行简称获取总行id
    public static String getBankInfoAbbreviation(String shortName){
        DbBankInfo dbBankInfo=bankInfoDao.queryBuilder().where(DbBankInfoDao.Properties.BankShortName.eq(shortName)).unique();
        return dbBankInfo.getBankId();
    }

    public TitleBar getTitleBar() {
        if (getTitleBarId() > 0 && findViewById(getTitleBarId()) instanceof TitleBar) {
            return findViewById(getTitleBarId());
        }
        return null;
    }

    @Override
    public boolean statusBarDarkFont() {
        //返回true表示黑色字体
        return true;
    }

    /**
     * {@link OnTitleBarListener}
     */

    // 标题栏左边的View被点击了
    @Override
    public void onLeftClick(View v) {
        onBackPressed();
    }

    // 标题栏中间的View被点击了
    @Override
    public void onTitleClick(View v) {}

    // 标题栏右边的View被点击了
    @Override
    public void onRightClick(View v) {}

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计

    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mButterKnife != null) mButterKnife.unbind();
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(int id) {
        ToastUtils.show(id);
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }



    //跳转到浏览器
    public void startBrowserActivity(Context context,int mode, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        startActivityForResult(intent, -1);
    }



    public class TimeCount extends CountDownTimer {

        TextView textView;

        public TimeCount(long millisInFuture, long countDownInterval,TextView textView) {
            super(millisInFuture, countDownInterval);
            this.textView=textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            textView.setTextColor(Color.parseColor("#8d8d8d"));
            textView.setClickable(false);
            textView.setText(+millisUntilFinished / 1000 +" 秒后可重新发送");
        }

        @Override
        public void onFinish() {
            textView.setText("重新获取验证码");
            textView.setClickable(true);
            textView.setTextColor(Color.parseColor("#0096ff"));

        }
    }

    //判断用户是否登录
    public boolean isLogin(){
        List<DbUserInfo> userInfos=userInfoDao.loadAll();
        if (userInfos==null||userInfos.size()==0){
            return false;
        }else {
            return true;
        }
    }

    //修改本地状态
    public void modifyStatus(String userId,int status){
      DbUserInfo userInfo= userInfoDao.queryBuilder().where(DbUserInfoDao.Properties.UserId.eq(userId)).build().unique();
      if (userInfo!=null){
          userInfo.setRegisterState(status);
          userInfoDao.update(userInfo);
      }
    }



    /**
     * 认证
     * @param registerState
     */
    public void Authentication(int registerState){
        if (1==registerState){
            //实名认证第一步
            startActivityFinish(IdentifyOneActivity.class);
        }else if (2==registerState){
            //实名认字第二部
            startActivityFinish(IdentifyTwoActivity.class);
        }else if (3==registerState){
            //实名认证第三部
            startActivityFinish(IdentifyThreeActivity.class);
        }else if (6==registerState){
            startActivityFinish(MainActivity.class);
        }else {
            startActivityFinish(MainActivity.class);
        }
    }


    //获取进件状态
    public void queryRegisterState(Context context){
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign=TokenUtils.getSign(TokenUtils.objectMap(null),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().queryRegisterState(currentTimeMillis,sign,getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<RegisterStateResp>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<RegisterStateResp>t) throws Exception {

                        //修改状态
                        modifyStatus(getUserId(),Integer.parseInt(t.getData().getRegisterState()));
                        //保存数据
                        respDao.save(new RegisterStateResp(null,t.getData().getRegisterState(),t.getData().getIsOpenDateRepayment()));
                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            toast("网络连接失败，请联系管理员");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry <RegisterStateResp>t) {
                        toast(t.getMsg());
                    }
                });
    }

    //获取身份证识别token
    public void getOcrSing() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken result) {
                String token = result.getAccessToken();
                Log.i("getOcrSing", "成功:" + "," + token);
            }
            @Override
            public void onError(OCRError error) {
                // 调用失败，返回OCRError子类SDKError对象
                Log.e("getOcrSing", "失败:" + error);
            }
        }, getApplicationContext());
    }

    //是否存在总行数据
    public boolean isExistenceBankInfo(){
        if (bankInfoDao.loadAll().size()==0||bankInfoDao.loadAll()==null) {
            return false;
        }else {
            return true;
        }
    }


    //获取银行卡类型
    public void validateCardType(final int type, String cardNumber){
        OkHttp.getAsync("https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo="+cardNumber+"&cardBinCheck=true", new OkHttp.DataCallBack() {
            @Override
            public void requestSuccess(String result) throws Exception {
                ValideCard valideCard=new Gson().fromJson(result,ValideCard.class);
                //验证信用卡
                if (type==1){
                    if (!valideCard.equals("CC")){

                    }
                }else if (type==2){
                    if (!valideCard.equals("DC")){

                    }
                }
            }

            @Override
            public void requestFailure(Request request, IOException e) {

            }
        });
    }



}