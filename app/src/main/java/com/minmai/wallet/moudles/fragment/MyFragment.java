package com.minmai.wallet.moudles.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.minmai.wallet.moudles.ui.me.MessageBoardActivity;
import com.minmai.wallet.moudles.ui.me.SetupActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 */
public class MyFragment extends MyLazyFragment {

    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_user_no)
    TextView tvUserNo;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.ly_integral)
    AutoLinearLayout lyIntegral;
    @BindView(R.id.credit_num)
    TextView creditNum;
    @BindView(R.id.ly_credit_num)
    AutoLinearLayout lyCreditNum;
    @BindView(R.id.savings_num)
    TextView savingsNum;
    @BindView(R.id.ly_savings_num)
    AutoLinearLayout lySavingsNum;
    @BindView(R.id.tv_agency)
    TextView tvAgency;
    @BindView(R.id.ly_agency)
    AutoLinearLayout lyAgency;
    @BindView(R.id.video_recyclerView)
    RecyclerView videoRecyclerView;
    @BindView(R.id.tv_my_video)
    AutoLinearLayout tvMyVideo;
    @BindView(R.id.tv_message)
    AutoLinearLayout tvMessage;
    @BindView(R.id.tv_trade_record)
    AutoLinearLayout tvTradeRecord;
    @BindView(R.id.tv_share_download)
    AutoLinearLayout tvShareDownload;
    @BindView(R.id.my_customer)
    AutoLinearLayout myCustomer;
    @BindView(R.id.tv_online_customer)
    AutoLinearLayout tvOnlineCustomer;
    @BindView(R.id.tv_more_setup)
    AutoLinearLayout tvMoreSetup;
    @BindView(R.id.tv_help)
    AutoLinearLayout tvHelp;
    @BindView(R.id.ly_status)
    AutoLinearLayout lyStatus;

    List<String> list;
    @BindView(R.id.ly_salesman)
    AutoLinearLayout lySalesman;



    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        //获取状态栏高度
        Rect rectangle = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        LinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) lyStatus.getLayoutParams();
        lp.setMargins(0, rectangle.top, 0, 0);
        lyStatus.setLayoutParams(lp);
    }

    @Override
    protected void initData() {
        list = new ArrayList<>();
        list.add("完善个人信息");
        list.add("发布招聘信息");
        list.add("查看招聘信息");
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }


    @OnClick({R.id.ly_integral, R.id.ly_credit_num, R.id.ly_savings_num, R.id.ly_agency, R.id.tv_my_video, R.id.tv_message, R.id.tv_trade_record, R.id.tv_share_download, R.id.my_customer, R.id.tv_online_customer, R.id.tv_more_setup, R.id.tv_help,R.id.ly_salesman})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_integral:
                break;
            case R.id.ly_credit_num:
                ARouter.getInstance().build(ActivityConstant.CREDIT_CARD).navigation();
                break;
            case R.id.ly_savings_num:
                ARouter.getInstance().build(ActivityConstant.SAVING_CARD).navigation();
                break;
            case R.id.ly_agency:
                break;
            case R.id.tv_my_video:
                break;
            case R.id.tv_message:
                startActivity(MessageBoardActivity.class);
                break;
            case R.id.tv_trade_record:
                ARouter.getInstance().build(ActivityConstant.TRADE).navigation();
                break;
            case R.id.tv_share_download:
                break;
            case R.id.my_customer:
                break;
            case R.id.tv_online_customer:
                break;
            case R.id.tv_more_setup:
                startActivity(SetupActivity.class);
                break;
            case R.id.tv_help:
                break;
            case R.id.ly_salesman:
                new BottomDialog(getActivity(),false,list).show();
                break;
        }
    }


}
