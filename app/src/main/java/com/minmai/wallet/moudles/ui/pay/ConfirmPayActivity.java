package com.minmai.wallet.moudles.ui.pay;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.uitl.HideDataUtil;
import com.minmai.wallet.moudles.bean.request.QuickPayReq;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.IdentityAuth;
import com.minmai.wallet.moudles.bean.response.QuickPayResp;
import com.minmai.wallet.moudles.request.user.IdentifyContract;
import com.minmai.wallet.moudles.request.user.IdentifyPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 确认收款
 */
@Route(path = ActivityConstant.CONFIRM_PAY)
public class ConfirmPayActivity extends MyActivity implements IdentifyContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_savings)
    TextView tvSavings;
    @BindView(R.id.ly_select_card)
    AutoLinearLayout lySelectCard;
    @BindView(R.id.tv_real_name)
    TextView tvRealName;
    @BindView(R.id.tv_reserve_phone)
    TextView tvReservePhone;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.btn_next)
    Button btnNext;

    //储蓄卡id
    private String debitCardId;

    @Autowired
    String channelId;//渠道id

    @Autowired
    String money;//金额

    @Autowired
    String creditCardId;//信用卡id

    //城市
    private String city;//省，市，370100

    private IdentifyPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_confirm_pay;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setTitle("确认收款");

    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        presenter=new IdentifyPresenter(this,this);
        tvMoney.setText(money);
        getIdentify();
        getDebitCard();
    }

    //获取实名信息
    public void getIdentify(){
        presenter.queryIdentityAuth(getUserId());
    }

    //获取默认储蓄卡
    public void getDebitCard(){
        presenter.getDefaultDebitCardVo(getUserId());
    }

    @OnClick({R.id.ly_select_card, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_select_card:
                break;
            case R.id.btn_next:
                quickPay();
                break;
        }
    }

    public void quickPay(){
        QuickPayReq quickPayReq=new QuickPayReq();
        quickPayReq.setChannelId(channelId);
        quickPayReq.setCreditCardId(creditCardId);
        quickPayReq.setDebitCardId(debitCardId);
        quickPayReq.setMoney(money);
        quickPayReq.setCity("山东省,济南市,370100");
        quickPayReq.setReturnUrl(Constant.RETURN_URL);
        presenter.createQuickPay(getUserId(),quickPayReq);
    }

    @Override
    public void setIdentify(IdentityAuth identify) {
       tvRealName.setText(identify.getRealName());
    }

    @Override
    public void setDebitCard(DebitCard debitCard) {
        tvSavings.setText(HideDataUtil.formatCarNo(debitCard.getCarNumber()));
        tvReservePhone.setText(debitCard.getPhone());
        debitCardId=debitCard.getDebitCardId();
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void onSuccess(QuickPayResp quickPayResp) {
        startBrowserActivity(context,1,quickPayResp.getCodeUrl());
        finish();
    }
}
