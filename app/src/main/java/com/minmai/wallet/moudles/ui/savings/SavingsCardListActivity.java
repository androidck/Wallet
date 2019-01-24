package com.minmai.wallet.moudles.ui.savings;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.http.PATCH;

/**
 * 储蓄卡列表
 */
@Route(path = ActivityConstant.SAVING_CARD)
public class SavingsCardListActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.add_saving_card)
    AutoRelativeLayout addSavingCard;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_savings_card;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("选择储蓄卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setRightIcon(R.mipmap.choice_add_card);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightClick(View v) {
        startActivity(AddSavingCardActivity.class);
    }

    @OnClick(R.id.add_saving_card)
    public void onViewClicked() {
        startActivity(AddSavingCardActivity.class);
    }
}
