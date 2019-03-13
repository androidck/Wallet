package com.minmai.wallet.moudles.ui.upgrade;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.BottomDialogAdapter;
import com.minmai.wallet.moudles.adapter.UpgradeAdapter;
import com.minmai.wallet.moudles.bean.request.AlipayReq;
import com.minmai.wallet.moudles.bean.response.MemberCentreEntity;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.minmai.wallet.moudles.request.user.UpgradeContract;
import com.minmai.wallet.moudles.request.user.UpgradePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 升级
 */
@Route(path = ActivityConstant.UPGRADE)
public class UpgradeActivity extends MyActivity implements UpgradeContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_user_no)
    TextView tvUserNo;
    @BindView(R.id.tv_current)
    TextView tvCurrent;
    @BindView(R.id.tv_low)
    TextView tvLow;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.tv_high)
    TextView tvHigh;
    @BindView(R.id.ly_data)
    AutoLinearLayout lyData;
    private UpgradePresenter presenter;
    private UpgradeAdapter adapter;
    private BottomDialogAdapter adapters;

    List<String> dialogList = new ArrayList<>();
    BottomDialog bottomDialog;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_upgrade;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_white);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#2D2E2D"));
        tbLoginTitle.setTitle("会员中心");
        tbLoginTitle.setTitleColor(Color.parseColor("#ffffff"));
        refreshLayout.setEnableLoadMore(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    @Override
    protected void initData() {
        presenter = new UpgradePresenter(this, this);
        getUpgradeDate();
        dialogList.add("支付宝");
        adapters = new BottomDialogAdapter(context);
        adapters.setData(dialogList);
        adapter = new UpgradeAdapter(context);
        adapter.setOnPurchaseListener(new UpgradeAdapter.OnPurchaseListener() {
            @Override
            public void onItemPay(String gradeId, String money) {
                bottomDialog = new BottomDialog(context, false, gradeId, money, adapters, new BottomDialog.OnItemPayListener() {
                    @Override
                    public void onPay(final String upGradeId, final String upMoney) {
                        adapters.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
                            @Override
                            public void onClick(int position) {
                                callOrderPay(upGradeId, upMoney);
                            }
                        });
                    }
                });
                bottomDialog.show();
            }
        });
    }

    //获取升级信息
    public void getUpgradeDate() {
        presenter.getUpgradeDate(getUserId());
    }

    @Override
    public void success(String msg) {
        toast(msg);
        bottomDialog.dismiss();
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void upgradeDate(final MemberCentreEntity memberCentreEntity) {
        tvNickName.setText(memberCentreEntity.getNickName());
        tvUserNo.setText("ID：" + memberCentreEntity.getUserNo());
        tvCurrent.setText(memberCentreEntity.getLevelName());
        int ratio = (new Double((memberCentreEntity.getLevelRatio() * 100))).intValue();
        Glide.with(context).load(memberCentreEntity.getUserHead()).placeholder(R.mipmap.ic_launcher).into(imgHead);
        progressBar.setProgress(ratio);
        recyclerView.setAdapter(adapter);
        tvLow.setText(memberCentreEntity.getBeginLevel());
        tvHigh.setText(memberCentreEntity.getEndLevel());
        adapter.setData(memberCentreEntity.getList());
        adapter.notifyDataSetChanged();
    }


    //支付宝支付
    public void callOrderPay(String memberId, String money) {
        AlipayReq alipayReq = new AlipayReq();
        /*交易描述*/
        alipayReq.setBody("购买会员等级");
        /*商品的标题*/
        alipayReq.setSubject("会员升级");
        /*绝对超时时间*/
        alipayReq.setTimeoutExpress("30");
        /*商品类型*/
        alipayReq.setGoodsType("0");
        /*订单总金额*/
        alipayReq.setTotalAmount(money);
        /*订单类型*/
        alipayReq.setType("1");
        /*购买等级id*/
        alipayReq.setMemberLevelId(memberId);

        alipayReq.setUserId(getUserId());

        presenter.callOrderPay(alipayReq);
    }



}
