package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.uitl.MD5Utils;
import com.minmai.wallet.moudles.request.user.AccountContract;
import com.minmai.wallet.moudles.request.user.AccountPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改登录密码
 */
public class UpdateUserPassActivity extends MyActivity implements AccountContract.View {
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

    String oldPwd;
    String newPwd;
    String qrPwd;

    AccountPresenter presenter;
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
        presenter=new AccountPresenter(this,this);
    }



    @OnClick({R.id.tv_forgot_pass, R.id.btn_qr_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_forgot_pass:
                ARouter.getInstance().build(ActivityConstant.FORGET_PASSWORD).navigation();
                break;
            case R.id.btn_qr_update:
                startRequestInterface();
                break;
        }
    }

    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        oldPwd=tvOldPass.getText().toString().trim();
        newPwd=tvNewPass.getText().toString().trim();
        qrPwd=edQrPass.getText().toString().trim();
        if (TextUtils.isEmpty(oldPwd)){
            toast("原密码不能为空");
        }else if (TextUtils.isEmpty(newPwd)){
            toast("新密码不能为空");
        }else if (TextUtils.isEmpty(qrPwd)){
            toast("确认不能为空");
        }else if (!qrPwd.equals(newPwd)){
            toast("两次密码不一致");
        }else {
            presenter.uPUserPwwd(getUserId(),MD5Utils.stringToMD5(oldPwd),MD5Utils.stringToMD5(qrPwd));
        }
    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
        finish();
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setCodeId(String codeId) {

    }
}
