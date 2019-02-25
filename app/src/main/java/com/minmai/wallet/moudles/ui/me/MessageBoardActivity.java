package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.bean.response.LeavingMsg;
import com.minmai.wallet.moudles.request.leave.LeaveContract;
import com.minmai.wallet.moudles.request.leave.LeavePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

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
    }

    @Override
    protected void initData() {
        presenter=new LeavePresenter(this,this);
        getMessageList();
    }

    //获取列表数据
    public void getMessageList(){
        String userId=userInfoDao.loadAll().get(0).getUserId();
        LeavingMsg leavingMsg=new LeavingMsg();
        leavingMsg.setCurrentPage(currentPage+"");
        leavingMsg.setPageSize(pageSize+"");
        presenter.getListLevMessage(userId,leavingMsg);
    }

    @Override
    public void onContent(LeavingMsg leavingMsg) {

    }

    @Override
    public void fail(String msg) {

    }

    @Override
    public void noDate(int code) {
        toast(code);
    }
}
