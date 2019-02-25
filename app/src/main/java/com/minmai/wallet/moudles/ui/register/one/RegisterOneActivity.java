package com.minmai.wallet.moudles.ui.register.one;

import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.enumcode.EnumCodeUse;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册第一步
 */
@Route(path = ActivityConstant.USER_REGISTER_ONE)
public class RegisterOneActivity extends MyActivity implements UserContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

    String phone;

    private UserPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("注册");
        etLoginPhone.addTextChangedListener(new PhoneTextWatcher(etLoginPhone));
        presenter = new UserPresenter(this, this);
    }
    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
    }

    //必填验证
    protected void startRequestInterface() {
        phone=etLoginPhone.getText().toString().trim().replace(" ","");
        if (TextUtils.isEmpty(phone)){
            toast("手机号不能为空");
        }else if (!ValidateUtils.Mobile(phone)){
            toast("手机号格式不正确");
        }else {
            presenter.userRegisterSendMsg(EnumCodeUse.getEnumCodeUse(R.string.registered),phone);
        }
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        startRequestInterface();
    }



    @Override
    public void onSetContent(Object object) {

    }

    @Override
    public void onSetCodeId(String codeId) {
        ARouter.getInstance()
                .build(ActivityConstant.USER_REGISTER_TWO)
                .withString("codeId",codeId)
                .withString("phone",phone)
                .navigation();
    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setPerCenterInfo(PerCenterInfo perCenterInfo) {

    }
}
