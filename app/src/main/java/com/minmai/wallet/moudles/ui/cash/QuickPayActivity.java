package com.minmai.wallet.moudles.ui.cash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.common.uitl.HideDataUtil;
import com.minmai.wallet.common.uitl.StringUtil;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.minmai.wallet.moudles.bean.response.ChannelBank;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.dialog.PassagewayDialog;
import com.minmai.wallet.moudles.request.channel.ChannelContract;
import com.minmai.wallet.moudles.request.channel.ChannelPresenter;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.tv_channelSingleLimitUp)
    TextView tvChannelSingleLimitUp;
    @BindView(R.id.tv_rate)
    TextView tvRate;
    @BindView(R.id.tv_mer_type)
    TextView tvMerType;
    @BindView(R.id.tv_fee)
    TextView tvFee;
    @BindView(R.id.tv_scoreFlag)
    TextView tvScoreFlag;
    @BindView(R.id.tv_support_bank)
    TextView tvSupportBank;
    @BindView(R.id.ly_select_channel)
    AutoLinearLayout lySelectChannel;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.tv_bank_no)
    TextView tvBankNo;
    @BindView(R.id.tv_bank_nick)
    TextView tvBankNick;
    @BindView(R.id.img_big_logo)
    ImageView imgBigLogo;
    @BindView(R.id.ly_bank_bg)
    AutoRelativeLayout lyBankBg;
    @BindView(R.id.tv_card_bg)
    AutoLinearLayout tvCardBg;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    String handlingFee;//手续费
    @BindView(R.id.btn_next)
    Button btnNext;

    //通道id
    private String channelId;

    //最低限额
    private String limitUp;

    private String limitLow;

    private String money;
    //信用卡id
    private String creditId;

    //回调
    int RESULT_OK = 200;
    private String rate;//费率
    private String fee;//手续费

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
        /*初始化输入两位小数*/
        StringUtil.restrictionLength(2, edMoney);
        edMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    tvCharge.setText("手续费" + "0.00元");
                    btnNext.setVisibility(View.GONE);
                } else {
                    handlingFee = StringUtil.getFeeCalculation(s.toString(), rate, fee);
                    tvCharge.setText("手续费" + handlingFee);
                    btnNext.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter = new ChannelPresenter(this, this);
    }

    @OnClick({R.id.ly_add_card, R.id.tv_select_passageway, R.id.ly_select_channel, R.id.tv_card_bg,R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_add_card:
                ARouter.getInstance()
                        .build(ActivityConstant.CREDIT_CARD)
                        .withInt("quickPay", Constant.QUICK_KEY)
                        .navigation(this, 100);
                break;
            case R.id.tv_select_passageway:
                startRequestInterface();
                break;
            case R.id.ly_select_channel:
                startRequestInterface();
                break;
            case R.id.tv_card_bg:
                ARouter.getInstance()
                        .build(ActivityConstant.CREDIT_CARD)
                        .withInt("quickPay", Constant.QUICK_KEY)
                        .navigation(this, 100);
                break;
            case R.id.btn_next:
                checkNext();
                break;
        }
    }

    public void checkNext(){
        money=edMoney.getText().toString().trim();
        if (TextUtils.isEmpty(creditId)){
            toast("请选择信用卡");
        }else if (TextUtils.isEmpty(channelId)){
            toast("请选择通道");
        }else if (TextUtils.isEmpty(money)){
            toast("请输入消费金额");
        }else{
            compare(money,limitUp,limitLow);
        }
    }

    //获取通道数据
    public void getChannel() {
        presenter.queryChannel(getUserId());
    }

    @Override
    public void onRightClick(View v) {
        startActivity(CardKnowledgeActivity.class);
    }

    @Override
    public void setQueryChannel(List<Channel> channel) {
        showDialog(channel);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    public void getQuota() {
        presenter.queryBankLimit(creditId, channelId);
    }

    //获取限额
    @Override
    public void setQuota(ChannelBank channelBank) {
        tvTip.setText("最低限额：" + StringUtil.setMoneyConverter(channelBank.getTradingLimitLowY()) + "，" +
                "最高限额：" + StringUtil.setMoneyConverter(channelBank.getTradingLimitUpY()));
        limitUp=channelBank.getTradingLimitLowY();
        limitLow=channelBank.getTradingLimitUpY();
    }

    public void compare(String money,String limtUp,String limtDown){
        double d1=Double.parseDouble(money);
        double d2=Double.parseDouble(limtUp);
        double d3=Double.parseDouble(limtDown);
        if (d1<d2){
           toast("消费金额低于最低限额");
        }else if (d1>d3){
           toast("消费金额大于最高限额");
        }else {
            ARouter.getInstance()
                    .build(ActivityConstant.CONFIRM_PAY)
                    .withString("channelId",channelId)
                    .withString("money",money)
                    .withString("creditCardId",creditId)
                    .navigation();
            finish();
        }
    }

    @Override
    public void noData() {
        toast("暂无可用通道");
    }


    protected void startRequestInterface() {
        super.startRequestInterface();
        if (TextUtils.isEmpty(creditId)) {
            toast("请选择信用卡");
        } else {
            getChannel();
        }
    }

    public void setChannelView(Channel channel) {
        tvSelectPassageway.setVisibility(View.GONE);
        lySelectChannel.setVisibility(View.VISIBLE);
        tvChannelSingleLimitUp.setText(StringUtil.setMoneyConverter(channel.getChannelSingleLimitUp()));
        tvFee.setText("单笔手续费：" + TextUtil.format2(Double.valueOf(channel.getFee())) + "元");
        tvMerType.setText("商户类型：" + channel.getExtendedField1());
        if (channel.getScoreFlag().equals("1")) {
            tvScoreFlag.setText("有积分");
        } else if (channel.getScoreFlag().equals("2")) {
            tvScoreFlag.setText("无积分");
        }
        tvSupportBank.setText("支持银行：" + channel.getSupportBanks());
        tvRate.setText("费率：\t" + channel.getRate() + "%");
        //设置通道id
        channelId = channel.getChannelId();
        rate = channel.getRate();
        fee = channel.getFee();
    }

    //通道dialog
    public void showDialog(List<Channel> channels) {
        new PassagewayDialog(QuickPayActivity.this, channels, null, false, new PassagewayDialog.OnSelectChannelListener() {
            @Override
            public void onSelectChannel(int position, Channel channel) {
                setChannelView(channel);
                getQuota();
            }
        }).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                CreditCard creditCard = (CreditCard) data.getSerializableExtra("creditCard");
                setCardView(creditCard);
                creditId = creditCard.getCreditId();
            }
        }
    }

    //设置选择卡的View
    public void setCardView(CreditCard creditCard) {
        lyAddCard.setVisibility(View.GONE);
        tvCardBg.setVisibility(View.VISIBLE);
        tvBankName.setText(creditCard.getBankName());
        tvBankType.setText(creditCard.getType());
        tvBankNo.setText(HideDataUtil.formatCarNo(creditCard.getCarNumber()));
        Glide.with(context).load(creditCard.getLogo()).into(bankImg);
        Glide.with(context).load(creditCard.getBankBackground()).into(imgBigLogo);
        tvCardBg.setBackground(ViewUtil.setDrawable(creditCard.getBackgroundColor()));
        tvBankNick.setText(creditCard.getCreditAlias());
    }


}
