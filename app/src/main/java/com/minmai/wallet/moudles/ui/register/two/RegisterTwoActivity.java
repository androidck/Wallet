package com.minmai.wallet.moudles.ui.register.two;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;

import butterknife.BindView;

/**
 * 注册第二步
 */
@Route(path = ActivityConstant.USER_REGISTER_TWO)
public class RegisterTwoActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

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
        tbLoginTitle.setTitle("注册");
    }

    @Override
    protected void initData() {

    }


}
