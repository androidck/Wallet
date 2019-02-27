package com.minmai.wallet.moudles.ui.cash;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.dialog.PassagewayDialog;
import com.minmai.wallet.moudles.request.channel.ChannelContract;
import com.minmai.wallet.moudles.request.channel.ChannelPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 快捷刷卡
 */
@Route(path = ActivityConstant.QUICK_PAY)
public class QuickPayActivity extends MyActivity implements ChannelContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.ly_add_card)
    AutoRelativeLayout lyAddCard;
    @BindView(R.id.tv_select_passageway)
    TextView tvSelectPassageway;
    @BindView(R.id.ed_money)
    ClearEditText edMoney;
    @BindView(R.id.tv_charge)
    TextView tvCharge;

    ChannelPresenter presenter;

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
        presenter=new ChannelPresenter(this,this);
    }

    @OnClick({R.id.ly_add_card, R.id.tv_select_passageway})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_add_card:
                ARouter.getInstance()
                        .build(ActivityConstant.CREDIT_CARD)
                        .navigation();
                break;
            case R.id.tv_select_passageway:
                getChannel();
                break;
        }
    }

    //获取通道数据
    public void getChannel(){
        presenter.queryChannel(getUserId());
    }

    @Override
    public void onRightClick(View v) {
        startActivity(CardKnowledgeActivity.class);
    }

    @Override
    public void setQueryChannel(List<Channel> channel) {
        new PassagewayDialog(QuickPayActivity.this,channel,false).show();
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void noData() {
        toast("暂无可用通道");
    }
}
