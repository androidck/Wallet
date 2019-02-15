package com.minmai.wallet.moudles.ui.me;

import android.widget.Button;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置提现密码
 */
public class SetupPasswordActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_new_pass)
    ClearEditText tvNewPass;
    @BindView(R.id.ed_qr_pass)
    ClearEditText edQrPass;
    @BindView(R.id.btn_qr_update)
    Button btnQrUpdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_setup_pass;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("修改密码");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }


    @OnClick(R.id.btn_qr_update)
    public void onViewClicked() {
    }
}
