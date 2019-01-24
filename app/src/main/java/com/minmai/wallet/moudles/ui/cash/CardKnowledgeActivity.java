package com.minmai.wallet.moudles.ui.cash;

import android.os.Bundle;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 养卡小知识
 */
public class CardKnowledgeActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_card_know;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("养卡小知识");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }

}
