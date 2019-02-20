package com.minmai.wallet.moudles.ui.find;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.ui.cash.CardKnowledgeActivity;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 提现
 */
public class WithdrawCashActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_bank_log)
    ImageView imgBankLog;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_no)
    TextView tvBankNo;
    @BindView(R.id.ly_select_card)
    AutoLinearLayout lySelectCard;
    @BindView(R.id.ed_money)
    ClearEditText edMoney;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_all_cash)
    TextView tvAllCash;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_withdraw_cash;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("提现");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setRightIcon(R.mipmap.user_more_black);

    }

    @Override
    protected void initData() {

    }
    @Override
    public void onRightClick(View v) {
        startActivity(WithdrawCashDetailsActivity.class);
    }


    @OnClick({R.id.ly_select_card, R.id.tv_all_cash, R.id.btn_login_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_select_card:
                break;
            case R.id.tv_all_cash:
                break;
            case R.id.btn_login_commit:
                break;
        }
    }
}
