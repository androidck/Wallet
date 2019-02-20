package com.minmai.wallet.moudles.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.constant.Constant;
import com.minmai.wallet.moudles.ui.main.MainActivity;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 主页
 */
public class HomeFragment extends MyLazyFragment {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.banner)
    XBanner banner;
    @BindView(R.id.tv_notice)
    TextView tvNotice;
    @BindView(R.id.tv_quick_pay)
    TextView tvQuickPay;
    @BindView(R.id.tv_date_repayment)
    TextView tvDateRepayment;
    @BindView(R.id.tv_share_profit)
    TextView tvShareProfit;
    @BindView(R.id.tv_upgrade)
    TextView tvUpgrade;
    @BindView(R.id.lv_finance_service)
    AutoLinearLayout lvFinanceService;
    @BindView(R.id.tv_life_service)
    AutoLinearLayout tvLifeService;
    @BindView(R.id.tv_credit_card_knowledge)
    AutoLinearLayout tvCreditCardKnowledge;
    @BindView(R.id.ly_extension)
    AutoLinearLayout lyExtension;
    @BindView(R.id.ly_loan)
    AutoLinearLayout lyLoan;
    @BindView(R.id.ly_network_online)
    AutoLinearLayout lyNetworkOnline;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
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
        tbLoginTitle.setTitle("首页");
        tbLoginTitle.setTitleColor(Color.parseColor("#323232"));
    }

    @Override
    protected void initData() {

    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }


    @OnClick({R.id.banner, R.id.tv_notice, R.id.tv_quick_pay, R.id.tv_date_repayment, R.id.tv_share_profit, R.id.tv_upgrade, R.id.lv_finance_service, R.id.tv_life_service, R.id.tv_credit_card_knowledge, R.id.ly_extension, R.id.ly_loan,R.id.ly_network_online})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.banner:
                break;
            case R.id.tv_notice:
                break;
            case R.id.tv_quick_pay:
                ARouter.getInstance().build(ActivityConstant.QUICK_PAY).navigation();
                break;
            case R.id.tv_date_repayment:
                ARouter.getInstance().build(ActivityConstant.DATE_REPAYMENT).navigation();
                break;
            case R.id.tv_share_profit:
                ARouter.getInstance().build(ActivityConstant.MY_SHARE_MOIST).navigation();
                break;
            case R.id.tv_upgrade:
                ARouter.getInstance().build(ActivityConstant.UPGRADE).navigation();
                break;
            case R.id.lv_finance_service:
                break;
            case R.id.tv_life_service:
                break;
            case R.id.tv_credit_card_knowledge:
                break;
            case R.id.ly_extension:

                break;
            case R.id.ly_loan:
                break;
            case R.id.ly_network_online:
                startBrowserActivity(getActivity(), MainActivity.MODE_SONIC, Constant.CREDIT_CARD_URL);
                break;
        }
    }


}
