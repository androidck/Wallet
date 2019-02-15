package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改登录密码
 */
public class UpdateUserPassActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_old_pass)
    ClearEditText tvOldPass;
    @BindView(R.id.tv_forgot_pass)
    TextView tvForgotPass;
    @BindView(R.id.tv_new_pass)
    ClearEditText tvNewPass;
    @BindView(R.id.ed_qr_pass)
    ClearEditText edQrPass;
    @BindView(R.id.btn_qr_update)
    Button btnQrUpdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_update_pass;
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


    @OnClick({R.id.tv_forgot_pass, R.id.btn_qr_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pass:
                ARouter.getInstance().build(ActivityConstant.FORGET_PASSWORD).navigation();
                break;
            case R.id.btn_qr_update:
                break;
        }
    }
}
