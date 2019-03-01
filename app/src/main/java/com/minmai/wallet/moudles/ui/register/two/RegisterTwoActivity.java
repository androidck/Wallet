package com.minmai.wallet.moudles.ui.register.two;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.enumcode.EnumCodeUse;
import com.minmai.wallet.common.view.VerificationCodeInput;
import com.minmai.wallet.moudles.bean.request.UserInfoReq;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;

import butterknife.BindView;
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

    private String code;//验证码

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
        ARouter.getInstance().inject(this);
        timeCount = new TimeCount(60000, 1000,tvSendMsg);
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
        UserInfoReq userInfoReq=new UserInfoReq();
        userInfoReq.setCodeId(codeId);
        userInfoReq.setPhone(phone);
        userInfoReq.setCode(content);
        presenter.userRegisterValidateCode(userInfoReq);
        code=content;
    }


    @Override
    public void onSetContent(Object object) {

    }

    @Override
    public void onSetCodeId(String codeId) {
        //保存新的验证码ID
        this.codeId=codeId;
    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
        if ("发送成功".equals(msg)){
            toast(msg);
        }else if ("访问成功".equals(msg)){
            ARouter.getInstance()
                    .build(ActivityConstant.USER_REGISTER_THREE)
                    .withString("codeId",codeId)
                    .withString("phone",phone)
                    .withString("code",code)
                    .navigation();
            finish();
        }

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setPerCenterInfo(PerCenterInfo perCenterInfo) {

    }


    @OnClick(R.id.tv_send_msg)
    public void onViewClicked() {
        //重新发送验证码
        presenter.userRegisterSendMsg(EnumCodeUse.getEnumCodeUse(R.string.registered),phone);
        timeCount.start();
    }


}
