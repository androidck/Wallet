package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class OpinionActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.ed_opinion)
    EditText edOpinion;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opinion;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("意见反馈");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
    }
}
