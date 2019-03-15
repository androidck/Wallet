package com.minmai.wallet.moudles.ui.startup;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.OnPermission;
import com.hjq.permissions.XXPermissions;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.permission.Permission;
import com.minmai.wallet.moudles.ui.identity.IdentifyOneActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 启动页面
 */
public class StartupActivity extends MyActivity
        implements OnPermission, Animation.AnimationListener {

    public static int TIME_OUT_GO=3000;

    @BindView(R.id.btn_skip)
    Button btnSkip;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_start_up;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        //初始化动画
        //设置状态栏和导航栏参数
    }

    @Override
    protected void initData() {
        if (XXPermissions.isHasPermission(StartupActivity.this, Permission.Group.STORAGE,Permission.Group.LOCATION)) {
            hasPermission(null, true);
        } else {
            requestFilePermission();
        }
    }

    private static final int ANIM_TIME = 1000;

    /**
     * 启动动画
     */


    private void requestFilePermission() {
        XXPermissions.with(this)
                .permission(Permission.Group.STORAGE,Permission.Group.LOCATION)
                .request(this);
    }

    /**
     * {@link OnPermission}
     */

    @Override
    public void hasPermission(List<String> granted, boolean isAll) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ARouter.getInstance().build(ActivityConstant.MAIN).navigation();
                finish();
            }
        },TIME_OUT_GO);
    }

    @Override
    public void noPermission(List<String> denied, boolean quick) {
        if (quick) {
            toast("没有权限访问文件，请手动授予权限");
            XXPermissions.gotoPermissionSettings(StartupActivity.this, true);
        } else {
            toast("请先授予文件读写权限");
            getWindow().getDecorView().postDelayed(new Runnable() {
                @Override
                public void run() {
                    requestFilePermission();
                }
            }, 2000);
        }
    }


    @Override
    public void onBackPressed() {
        //禁用返回键
        //super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }

    @Override
    public boolean isSupportSwipeBack() {
        //不使用侧滑功能
        return !super.isSupportSwipeBack();
    }

    /**
     * {@link Animation.AnimationListener}
     */

    @Override
    public void onAnimationStart(Animation animation) {
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        requestFilePermission();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    /**
     * Android 8.0踩坑记录：Only fullscreen opaque activities can request orientation
     * https://www.jianshu.com/p/d0d907754603
     */
    @Override
    protected void initOrientation() {
        // 注释父类的固定屏幕方向方法
        // super.initOrientation();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
