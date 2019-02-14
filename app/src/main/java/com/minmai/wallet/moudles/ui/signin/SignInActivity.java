package com.minmai.wallet.moudles.ui.signin;

import android.graphics.Color;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 用户签到
 */
@Route(path = ActivityConstant.SIGN_IN)
public class SignInActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_sign_in;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("签到");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_white);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#0096ff"));

    }

    @Override
    protected void initData() {

    }


}
