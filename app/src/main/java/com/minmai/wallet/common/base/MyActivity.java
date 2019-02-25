package com.minmai.wallet.common.base;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.OnTitleBarListener;
import com.hjq.bar.TitleBar;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.ui.identity.IdentifyOneActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyThreeActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyTwoActivity;
import com.minmai.wallet.moudles.ui.main.MainActivity;
import com.minmai.wallet.moudles.web.BrowserActivity;
import com.minmai.wallet.moudles.web.SonicJavaScriptInterface;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中的 Activity 基类
 */
public abstract class MyActivity extends UIActivity
        implements OnTitleBarListener {

    private Unbinder mButterKnife;//View注解

    public DbUserInfoDao userInfoDao;

    int currentPage=1;
    int pageSize=10;

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

    /**
     * 倒计时
     * @param textView
     */
    public void countDown(final TextView textView){
        /** 倒计时60秒，一次1秒 */
        CountDownTimer timer = new CountDownTimer(10*1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // TODO Auto-generated method stub
                textView.setText(millisUntilFinished/1000+"秒后重新获取");
                textView.setClickable(false);
                textView.setTextColor(Color.parseColor("#8d8d8d"));
            }
            @Override
            public void onFinish() {
                textView.setText("重新发送验证码");
                textView.setClickable(true);
                textView.setTextColor(Color.parseColor("#0096ff"));
            }
        }.start();


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



}