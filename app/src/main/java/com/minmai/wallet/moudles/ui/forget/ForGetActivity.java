package com.minmai.wallet.moudles.ui.forget;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.enumcode.EnumCodeUse;
import com.minmai.wallet.common.uitl.MD5Utils;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.moudles.request.user.AccountContract;
import com.minmai.wallet.moudles.request.user.AccountPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 找回密码
 */
@Route(path = ActivityConstant.FORGET_PASSWORD)
public class ForGetActivity extends MyActivity implements AccountContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @BindView(R.id.et_msg_code)
    ClearEditText etMsgCode;
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;

    TimeCount timeCount;
    private AccountPresenter presenter;
    private String codeId;

    private String loginPhone;
    private String msgCode;
    private String newPwd;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_forget_pass;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        etLoginPhone.addTextChangedListener(new PhoneTextWatcher(etLoginPhone));
        tbLoginTitle.setTitle("找回秘密");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {
        presenter=new AccountPresenter(this,this);
        timeCount = new TimeCount(60000, 1000,tvLoginForget);
    }


    @OnClick({R.id.tv_login_forget, R.id.btn_login_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                loginPhone=etLoginPhone.getText().toString().trim().replace(" ","");
                if (TextUtils.isEmpty(loginPhone)){
                    toast("手机号不能为空");
                }else if (!ValidateUtils.Mobile(loginPhone)){
                    toast("手机号格式不正确");
                }else {
                    presenter.bindSendMsg(EnumCodeUse.getEnumCodeUse(R.string.modify_login_pwd),loginPhone);
                }
                break;
            case R.id.btn_login_commit:
                startRequestInterface();
                break;
        }
    }

    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        loginPhone=etLoginPhone.getText().toString().trim().replace(" ","");
        msgCode=etMsgCode.getText().toString().trim();
        newPwd=etLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(loginPhone)){
            toast("手机号不能为空");
        }else if (!ValidateUtils.Mobile(loginPhone)){
            toast("手机号格式不正确");
        }else if (TextUtils.isEmpty(msgCode)){
            toast("验证码不能为空");
        }else if (TextUtils.isEmpty(newPwd)){
            toast("密码不能为空");
        }else {
            presenter.forgetUserPwwd(loginPhone,msgCode,codeId,MD5Utils.stringToMD5(newPwd));
        }
    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
        timeCount.start();
        if ("修改成功".equals(msg)){
            toast(msg);
            finish();
        }
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setCodeId(String codeId) {
        this.codeId=codeId;
    }
}
