package com.minmai.wallet.moudles.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.moudles.ui.find.WithdrawCashActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 发现
 */
public class FindFragment extends MyLazyFragment {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_open_close)
    ImageView imgOpenClose;
    @BindView(R.id.img_sign_in)
    ImageView imgSignIn;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.tv_commission)
    TextView tvCommission;
    @BindView(R.id.tv_share_profit)
    TextView tvShareProfit;
    @BindView(R.id.tv_credit_card)
    TextView tvCreditCard;
    @BindView(R.id.tv_upgrade)
    TextView tvUpgrade;
    @BindView(R.id.tv_share_details)
    TextView tvShareDetails;
    @BindView(R.id.tv_profit)
    TextView tvProfit;
    @BindView(R.id.tv_all_count)
    TextView tvAllCount;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }


    @Override
    public boolean isStatusBarEnabled() {
        return true;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("发现");
        tbLoginTitle.setTitleColor(Color.parseColor("#323232"));
    }

    @Override
    protected void initData() {

    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }


    @OnClick({R.id.tb_login_title, R.id.img_open_close, R.id.img_sign_in, R.id.tv_money, R.id.tv_commission, R.id.tv_share_profit, R.id.tv_credit_card, R.id.tv_upgrade, R.id.tv_share_details, R.id.tv_profit, R.id.tv_all_count, R.id.recyclerView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tb_login_title:
                break;
            case R.id.img_open_close:
                break;
            case R.id.img_sign_in:

                break;
            case R.id.tv_money:
                break;
            case R.id.tv_commission:
                break;
            case R.id.tv_share_profit:
                break;
            case R.id.tv_credit_card:
                break;
            case R.id.tv_upgrade:
                break;
            case R.id.tv_share_details:
                break;
            case R.id.tv_profit:
                startActivity(WithdrawCashActivity.class);
                break;
            case R.id.tv_all_count:
                break;
            case R.id.recyclerView:
                break;
        }
    }
}
