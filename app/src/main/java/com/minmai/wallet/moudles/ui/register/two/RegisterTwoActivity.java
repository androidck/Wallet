package com.minmai.wallet.moudles.ui.register.two;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.uitl.EnumCodeUse;
import com.minmai.wallet.common.view.VerificationCodeInput;
import com.minmai.wallet.moudles.bean.UserInfo;
import com.minmai.wallet.moudles.request.UserContract;
import com.minmai.wallet.moudles.request.UserPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册第二步
 */
@Route(path = ActivityConstant.USER_REGISTER_TWO)
public class RegisterTwoActivity extends MyActivity implements UserContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

    @Autowired(name = "codeId")
    public String codeId;//验证码id
    @Autowired(name = "phone")
    public String phone;//手机号

    @BindView(R.id.verificationCodeInput)
    VerificationCodeInput verificationCodeInput;
    @BindView(R.id.tv_show_phone)
    TextView tvShowPhone;
    @BindView(R.id.tv_send_msg)
    TextView tvSendMsg;

    TimeCount timeCount;

    private UserPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_two;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        timeCount = new TimeCount(10000, 1000,tvSendMsg);
        ARouter.getInstance().inject(this);
        tbLoginTitle.setTitle("注册");
        presenter = new UserPresenter(this, this);
        tvShowPhone.setText("我们已向" + phone + "发送验证码短信");
        timeCount.start();
    }


    @Override
    protected void initData() {
        verificationCodeInput.setOnCompleteListener(new VerificationCodeInput.Listener() {
            @Override
            public void onComplete(String content) {
                //输入完成的内容
                request(content);
            }
        });
    }

    //网络请求
    private void request(String content) {
        presenter.userRegisterValidateCode(codeId, phone, content);
    }

    @Override
    public void setContent(UserInfo userInfo) {

    }

    @Override
    public void success(String msg, Object object) {
        toast(msg);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }




    @OnClick(R.id.tv_send_msg)
    public void onViewClicked() {
        //重新发送验证码
        //presenter.userRegisterSendMsg(EnumCodeUse.getEnumCodeUse(R.string.registered),phone);
        timeCount.start();
    }


}
