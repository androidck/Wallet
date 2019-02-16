package com.minmai.wallet.moudles.ui.me;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 更多设置
 */
public class SetupActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_account_security)
    TextView tvAccountSecurity;
    @BindView(R.id.tv_abount_me)
    TextView tvAbountMe;
    @BindView(R.id.tv_clean_cache)
    AutoLinearLayout tvCleanCache;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setup;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("更多设置");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_account_security, R.id.tv_clean_cache, R.id.btn_login_out,R.id.tv_abount_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_security:
                startActivity(ManageAcountActivity.class);
                break;
            case R.id.tv_clean_cache:
                break;
            case R.id.btn_login_out:
                break;
            case R.id.tv_abount_me:
                startActivity(AboutUsActivity.class);
                break;
        }
    }
}
