package com.minmai.wallet.moudles.ui.daterepayment;

import android.graphics.Color;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;

/**
 * 制定日期计划
 */
public class FormulateDatePlanActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_formulate;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("制定日期还款");
        tbLoginTitle.setTitleColor(Color.parseColor("#ffffff"));
        tbLoginTitle.setBackgroundColor(Color.parseColor("#00000000"));
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_white);
    }

    @Override
    protected void initData() {

    }


}
