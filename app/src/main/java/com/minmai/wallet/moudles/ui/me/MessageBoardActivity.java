package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.adapter.MessageBoardAdapter;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.bean.response.ListLeaving;
import com.minmai.wallet.moudles.request.leave.LeaveContract;
import com.minmai.wallet.moudles.request.leave.LeavePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 留言板
 */
public class MessageBoardActivity extends MyActivity implements LeaveContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    LeavePresenter presenter;
    int currentPage;
    int pageSize;
    @BindView(R.id.ly_layout)
    AutoRelativeLayout lyLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private MessageBoardAdapter adapter;

    private List<ListLeaving> list;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_message_board;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("留言板");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage++;
                        getMessageList();
                        refreshLayout.finishLoadMore();
                    }
                },300);
            }

            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        currentPage=1;
                        getMessageList();
                        refreshLayout.finishRefresh();
                    }
                },300);
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new LeavePresenter(this, this);
        adapter = new MessageBoardAdapter(context);
        getMessageList();
    }

    //获取列表数据
    public void getMessageList() {
        String userId = userInfoDao.loadAll().get(0).getUserId();
        ListBaseData leavingMsg = new ListBaseData();
        leavingMsg.setPageCurrent(currentPage);
        leavingMsg.setPageSize(pageSize);
        presenter.getListLevMessage(userId, leavingMsg);
    }

    @Override
    public void onContent(ListBaseData<ListLeaving> leavingMsg) {
        lyLayout.setVisibility(View.GONE);
        list = leavingMsg.getList();
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        if (leavingMsg.getTotalCount().equals(list.size())){
            refreshLayout.setEnableLoadMore(false);
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
