package com.minmai.wallet.moudles.ui.identity;

import android.graphics.Color;
import android.os.Bundle;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 完善信息第一步
 */
public class IdentifyOneActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("完善信息");
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
