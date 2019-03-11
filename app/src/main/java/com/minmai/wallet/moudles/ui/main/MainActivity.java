package com.minmai.wallet.moudles.ui.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.widget.NoScrollViewPager;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.helper.ActivityStackManager;
import com.minmai.wallet.common.helper.DoubleClickHelper;
import com.minmai.wallet.common.jpush.JpushUtil;
import com.minmai.wallet.moudles.adapter.HomeFragmentAdapter;

import butterknife.BindView;
import cn.jpush.android.api.JPushInterface;

/**
 * 首页
 */
@Route(path = ActivityConstant.MAIN)
public class MainActivity extends MyActivity implements ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener  {


    @BindView(R.id.vp_home_pager)
    NoScrollViewPager vpHomePager;
    @BindView(R.id.bv_home_navigation)
    BottomNavigationView bvHomeNavigation;

    private HomeFragmentAdapter mPagerAdapter;

    public static final int MODE_DEFAULT = 0;

    public static final int MODE_SONIC = 1;

    public static final int MODE_SONIC_WITH_OFFLINE_CACHE = 2;

    private static final int PERMISSION_REQUEST_CODE_STORAGE = 1;

    public static boolean isForeground = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {


        vpHomePager.addOnPageChangeListener(this);

        // 不使用图标默认变色
        bvHomeNavigation.setItemIconTintList(null);
        bvHomeNavigation.setOnNavigationItemSelectedListener(this);

        //注册极光推送广播
        registerMessageReceiver();
        init();


    }

    @Override
    protected void initData() {
        mPagerAdapter = new HomeFragmentAdapter(this);
        vpHomePager.setAdapter(mPagerAdapter);
        // 限制页面数量
        vpHomePager.setOffscreenPageLimit(mPagerAdapter.getCount());

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                bvHomeNavigation.setSelectedItemId(R.id.menu_home);
                break;
            case 1:
                bvHomeNavigation.setSelectedItemId(R.id.home_found);
                break;
            case 2:
                bvHomeNavigation.setSelectedItemId(R.id.home_message);
                break;
            case 3:
                bvHomeNavigation.setSelectedItemId(R.id.home_me);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {}

    /**
     * {@link BottomNavigationView.OnNavigationItemSelectedListener}
     */

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                vpHomePager.setCurrentItem(0);
                return true;
            case R.id.home_found:
                vpHomePager.setCurrentItem(1);
                return true;
            case R.id.home_message:
                vpHomePager.setCurrentItem(2);
                return true;
            case R.id.home_me:
                vpHomePager.setCurrentItem(3);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (DoubleClickHelper.isOnDoubleClick()) {
            //移动到上一个任务栈，避免侧滑引起的不良反应
            moveTaskToBack(false);
            getHandler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // 进行内存优化，销毁掉所有的界面
                    ActivityStackManager.getInstance().finishAllActivities();
                }
            }, 300);
        } else {
            toast(getResources().getString(R.string.home_exit_hint));
        }
    }

    // 初始化 JPush。如果已经初始化，但没有登录成功，则执行重新登录。
    private void init(){
        JPushInterface.init(getApplicationContext());
    }


    @Override
    protected void onResume() {
        isForeground = true;
        super.onResume();
    }


    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        vpHomePager.removeOnPageChangeListener(this);
        vpHomePager.setAdapter(null);
        bvHomeNavigation.setOnNavigationItemSelectedListener(null);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
    }

    @Override
    public boolean isSupportSwipeBack() {
        // 不使用侧滑功能
        return !super.isSupportSwipeBack();
    }


    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    /**
     * 极光推送广播
     */
    public class MessageReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!JpushUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                }
            } catch (Exception e){
            }
        }
    }


}
