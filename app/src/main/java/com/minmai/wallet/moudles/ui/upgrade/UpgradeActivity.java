package com.minmai.wallet.moudles.ui.upgrade;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 升级
 */
@Route(path = ActivityConstant.UPGRADE)
public class UpgradeActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.ly_data)
    AutoLinearLayout lyData;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_upgrade;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_white);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#2D2E2D"));
        tbLoginTitle.setTitle("会员中心");
        tbLoginTitle.setTitleColor(Color.parseColor("#ffffff"));
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    @Override
    protected void initData() {

    }

}
