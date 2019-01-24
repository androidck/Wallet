package com.minmai.wallet.moudles.ui.identity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 完善信息第三部
 */
public class IdentifyThreeActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.btn_next)
    Button btnNext;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identify_three;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("完善信息");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void initData() {

    }

}
