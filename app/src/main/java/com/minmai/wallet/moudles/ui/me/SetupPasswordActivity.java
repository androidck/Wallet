package com.minmai.wallet.moudles.ui.me;

import android.text.TextUtils;
import android.widget.Button;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.uitl.MD5Utils;
import com.minmai.wallet.moudles.request.user.AccountContract;
import com.minmai.wallet.moudles.request.user.AccountPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 设置提现密码
 */
public class SetupPasswordActivity extends MyActivity implements AccountContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_new_pass)
    ClearEditText tvNewPass;
    @BindView(R.id.ed_qr_pass)
    ClearEditText edQrPass;
    @BindView(R.id.btn_qr_update)
    Button btnQrUpdate;
    AccountPresenter presenter;

    private String newPass;
    private String newQrPass;

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
        presenter=new AccountPresenter(this,this);
    }


    @OnClick(R.id.btn_qr_update)
    public void onViewClicked() {
        startRequestInterface();
    }


    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        newPass=tvNewPass.getText().toString().trim();
        newQrPass=edQrPass.getText().toString().trim();
        if (TextUtils.isEmpty(newPass)){
            toast("密码不能为空");
        }else if (TextUtils.isEmpty(newQrPass)){
            toast("确认密码不能为空");
        }else if (!(newQrPass.equals(newPass))){
            toast("两次密码不一致");
        }else {
            presenter.addPaymentPassword(getUserId(),MD5Utils.stringToMD5(newQrPass));
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
