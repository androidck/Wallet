package com.minmai.wallet.moudles.fragment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.moudles.bean.response.UserGounpCount;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.request.find.FoundPageContract;
import com.minmai.wallet.moudles.request.find.FoundPagePresenter;
import com.minmai.wallet.moudles.ui.find.WithdrawCashActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发现
 */
public class FindFragment extends MyLazyFragment implements FoundPageContract.View {
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

    DbUserInfoDao userInfoDao;
    private Context context;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_find;
    }

    //默认显示金额
    private int isMoneyType=1;
    FoundPagePresenter presenter;

    private String allMoney,yjMoney,frMoney;

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
        context=getActivity();
        tbLoginTitle.setTitle("发现");
        tbLoginTitle.setTitleColor(Color.parseColor("#323232"));
    }

    @Override
    protected void initData() {
        userInfoDao= MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        presenter=new FoundPagePresenter(context,this);
        getFindInfo();
    }

    public static FindFragment newInstance() {
        return new FindFragment();
    }


    @OnClick({ R.id.img_open_close, R.id.img_sign_in,  R.id.tv_credit_card, R.id.tv_upgrade, R.id.tv_share_details, R.id.tv_profit, R.id.tv_all_count, R.id.recyclerView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_open_close:
                showMoney();
                break;
            case R.id.img_sign_in:
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

    //显示/隐藏金额
    public void showMoney(){
        if (isMoneyType==1){
            isMoneyType=2;
            tvMoney.setText("*****");
            tvCommission.setText("*****");
            tvShareProfit.setText("*****");
            imgOpenClose.setImageResource(R.mipmap.find_eye_close);
        }else if (isMoneyType==2){
            isMoneyType=1;
            tvMoney.setText(allMoney);
            tvCommission.setText(yjMoney);
            tvShareProfit.setText(frMoney);
            imgOpenClose.setImageResource(R.mipmap.find_eye_open);
        }
    }

    //获取发现页面数据
    public void getFindInfo(){
        List<DbUserInfo> userInfos=userInfoDao.loadAll();
        presenter.getFoundPageAllInitInfo(userInfos.get(0).getUserId());
    }


    @Override
    public void setContent(Object o) {
        UserGounpCount userGounpCount= (UserGounpCount) o;
        allMoney=userGounpCount.getTotalBalance().toString();
        yjMoney=userGounpCount.getYjMoney().toString();
        frMoney=userGounpCount.getFeMoney().toString();
        tvMoney.setText(allMoney);
        tvCommission.setText(yjMoney);
        tvShareProfit.setText(frMoney);
        tvAllCount.setText(userGounpCount.getCountByNum()+"人");
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }
}
