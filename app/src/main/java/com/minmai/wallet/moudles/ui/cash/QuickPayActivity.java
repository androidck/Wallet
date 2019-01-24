package com.minmai.wallet.moudles.ui.cash;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快捷刷卡
 */
@Route(path = ActivityConstant.QUICK_PAY)
public class QuickPayActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.ly_add_card)
    AutoRelativeLayout lyAddCard;
    @BindView(R.id.tv_select_passageway)
    TextView tvSelectPassageway;
    @BindView(R.id.ed_money)
    EditText edMoney;
    @BindView(R.id.tv_charge)
    TextView tvCharge;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_quick_pay;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("刷卡消费");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setRightIcon(R.mipmap.little_knoeledge);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.ly_add_card, R.id.tv_select_passageway})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_add_card:
                break;
            case R.id.tv_select_passageway:
                break;
        }
    }

    @Override
    public void onRightClick(View v) {
        startActivity(CardKnowledgeActivity.class);
    }
}
