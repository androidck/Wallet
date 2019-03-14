package com.minmai.wallet.moudles.ui.upgrade;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alipay.sdk.app.PayTask;
import com.bumptech.glide.Glide;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.BottomDialogAdapter;
import com.minmai.wallet.moudles.adapter.UpgradeAdapter;
import com.minmai.wallet.moudles.bean.request.AlipayReq;
import com.minmai.wallet.moudles.bean.request.AuthResult;
import com.minmai.wallet.moudles.bean.request.PayResult;
import com.minmai.wallet.moudles.bean.response.MemberCentreEntity;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.minmai.wallet.moudles.request.user.UpgradeContract;
import com.minmai.wallet.moudles.request.user.UpgradePresenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import butterknife.BindView;
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

    private String alipayStr;//支付字符串
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() {
                //解决ScrollView里存在多个RecyclerView时滑动卡顿的问题
                //如果你的RecyclerView是水平滑动的话可以重写canScrollHorizontally方法
                return false;
            }
        });
        //解决数据加载不完的问题
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        //解决数据加载完成后, 没有停留在顶部的问题
        recyclerView.setFocusable(false);
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
        alipayStr=msg;
        bottomDialog.dismiss();
        alipayPay();
    }

    public void alipayPay(){
        final String orderInfo = alipayStr;   // 订单信息
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(UpgradeActivity.this);
                Map<String,String> result = alipay.payV2(orderInfo,true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SDK_PAY_FLAG:
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        toast("支付成功");
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        toast("支付失败");
                    }
                    break;
                case SDK_AUTH_FLAG:
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatuses = authResult.getResultStatus();
                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatuses, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        toast("支付成功");
                    } else {
                        // 其他状态值则为授权失败
                        toast("支付失败");
                    }
                    break;
            }
        };
    };

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
