package com.minmai.wallet.moudles.ui.daterepayment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hjq.bar.TitleBar;
import com.hjq.widget.NoScrollViewPager;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.MyPagerAdapter;
import com.minmai.wallet.moudles.ui.daterepayment.fragment.RepaymentListFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 日期还款
 */
@Route(path = ActivityConstant.DATE_REPAYMENT)
public class DateRepayMentActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;

    List<Fragment> mFragmentList=new ArrayList<>();
    List<String> mList=new ArrayList<>();

    MyPagerAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_date_repayment;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("日期还款");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setRightIcon(R.mipmap.add_bill);
        mList.add("正在还款");
        mList.add("还款失败");
        mList.add("还款历史");
        mList.add("全部计划");
        for (int i=0;i<mList.size();i++){
            Fragment fragment=RepaymentListFragment.newInstance(mList.get(i));
            mFragmentList.add(fragment);
        }
        adapter = new MyPagerAdapter(getSupportFragmentManager(), mList, mFragmentList);
        //设置下划线宽度
        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onRightClick(View v) {
        super.onRightClick(v);
    }
}
