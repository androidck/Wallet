package com.minmai.wallet.moudles.ui.trade;

import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.bean.response.LeavingMsg;
import com.minmai.wallet.moudles.request.transaction.TradeContract;
import com.minmai.wallet.moudles.request.transaction.TradePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 交易记录
 */
@Route(path = ActivityConstant.TRADE)
public class TransactionActivity extends MyActivity implements TradeContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    TradePresenter presenter;
    @BindView(R.id.ly_layout)
    AutoRelativeLayout lyLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_trade;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("交易记录");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }


    @Override
    protected void initData() {
        presenter = new TradePresenter(this, this);
        getQueryTrade();
    }

    //获取交易记录
    public void getQueryTrade() {
        String userId = userInfoDao.loadAll().get(0).getUserId();
        LeavingMsg leavingMsg = new LeavingMsg();
        leavingMsg.setPageCurrent(currentPage);
        leavingMsg.setPageSize(pageSize);
        presenter.queryTradingRecord(userId, leavingMsg);
    }


    @Override
    public void onSetContent(LeavingMsg leavingMsg) {
        lyLayout.setVisibility(View.GONE);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void noDate() {
        lyLayout.setVisibility(View.VISIBLE);
    }

}
