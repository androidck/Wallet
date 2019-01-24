package com.minmai.wallet.moudles.ui.register.three;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.view.PhoneTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册第三部
 */
@Route(path = ActivityConstant.USER_REGISTER_THREE)
public class RegisterThreeActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;//标题
    @BindView(R.id.et_recommend_phone)
    ClearEditText etRecommendPhone;//手机号
    @BindView(R.id.tv_book)
    ImageView tvBook;//通讯录
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;//密码
    @BindView(R.id.is_check_read)
    CheckBox isCheckRead;//是否阅读协议
    @BindView(R.id.tv_clause)
    TextView tvClause;//协议
    @BindView(R.id.btn_register_commit)
    Button btnRegisterCommit;//注册

    @Override
    protected int getLayoutId() {
        tbLoginTitle.setTitle(R.string.tv_register_title);
        return R.layout.activity_register_three;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        etRecommendPhone.addTextChangedListener(new PhoneTextWatcher(etRecommendPhone));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_book, R.id.tv_clause, R.id.btn_register_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_book:
                break;
            case R.id.tv_clause:
                break;
            case R.id.btn_register_commit:
                break;
        }
    }
}
