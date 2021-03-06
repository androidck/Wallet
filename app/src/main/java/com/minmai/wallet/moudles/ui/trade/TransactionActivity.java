package com.minmai.wallet.moudles.ui.trade;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.TradeAdapter;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.Trade;
import com.minmai.wallet.moudles.request.transaction.TradeContract;
import com.minmai.wallet.moudles.request.transaction.TradePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    TradeAdapter adapter;



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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage++;
                        getQueryTrade();
                        refreshLayout.finishLoadMore();
                    }
                },150);
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getQueryTrade();
                        refreshLayout.finishRefresh();
                    }
                },150);
            }
        });
    }


    @Override
    protected void initData() {
        presenter = new TradePresenter(this, this);
        adapter=new TradeAdapter(context);
        getQueryTrade();

    }

    //获取交易记录
    public void getQueryTrade() {
        String userId = userInfoDao.loadAll().get(0).getUserId();
        ListBaseData leavingMsg = new ListBaseData();
        leavingMsg.setPageCurrent(currentPage);
        leavingMsg.setPageSize(pageSize);
        presenter.queryTradingRecord(userId, leavingMsg);
    }


    @Override
    public void onSetContent(ListBaseData<Trade> leavingMsg) {
        lyLayout.setVisibility(View.GONE);
        adapter.setData(leavingMsg.getList());
        recyclerView.setAdapter(adapter);
        if (leavingMsg.getList().size()==Integer.parseInt(leavingMsg.getTotalCount())){
            refreshLayout.setEnableLoadMore(false);
        }else {
            refreshLayout.setEnableLoadMore(true);
        }
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
