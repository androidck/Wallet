package com.minmai.wallet.moudles.ui.register.one;

import android.os.Bundle;
import android.widget.Button;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.view.PhoneTextWatcher;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册第一步
 */
@Route(path = ActivityConstant.USER_REGISTER_ONE)
public class RegisterOneActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;
    @BindView(R.id.btn_next)
    Button btnNext;

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
        etLoginPhone.addTextChangedListener(new PhoneTextWatcher(etLoginPhone));
        tbLoginTitle.setTitle("注册");
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        ARouter.getInstance().build(ActivityConstant.USER_REGISTER_TWO).navigation();
    }
}
