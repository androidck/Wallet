package com.minmai.wallet.moudles.ui.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.NoticeAdapter;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.RollMessage;
import com.minmai.wallet.moudles.request.banner.BannerContract;
import com.minmai.wallet.moudles.request.banner.BannerPresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

import butterknife.BindView;

/**
 * 公告列表
 */
@Route(path = ActivityConstant.NOTICE)
public class NoticeActivity extends MyActivity implements BannerContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    private BannerPresenter presenter;

    private List<RollMessage> list;
    private NoticeAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_notice;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("公告");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        //关闭加载更多
        refreshLayout.setEnableLoadMore(false);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                refreshLayout.getLayout().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getRollMessage();
                        refreshLayout.finishRefresh();
                    }
                },300);
            }
        });
    }

    @Override
    protected void initData() {
        presenter=new BannerPresenter(this,this);
        adapter=new NoticeAdapter(context);
        getRollMessage();
    }

    //获取轮播消息
    public void getRollMessage(){
        presenter.getRollMessageList();
    }


    @Override
    public void setContent(List<BannerInfo> list) {

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setRollMessage(List<RollMessage> list) {
        this.list=list;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter.setData(this.list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
