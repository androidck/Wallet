package com.minmai.wallet.moudles.ui.daterepayment.fragment;

import android.os.Bundle;

import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyLazyFragment;

/**
 * 还款列表
 */
public class RepaymentListFragment extends MyLazyFragment {

    public static final String REPAY_MENT = "repay_ment";

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_repayment;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }


    public static RepaymentListFragment newInstance(String type){
        Bundle args = new Bundle();
        args.putString(REPAY_MENT, type);
        RepaymentListFragment fragment = new RepaymentListFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
