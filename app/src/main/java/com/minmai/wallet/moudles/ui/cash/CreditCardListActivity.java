package com.minmai.wallet.moudles.ui.cash;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.moudles.adapter.CrediteCardAdapter;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.minmai.wallet.moudles.bean.response.ListBaseData;
import com.minmai.wallet.moudles.dialog.OtherDialog;
import com.minmai.wallet.moudles.request.card.CreditCardContract;
import com.minmai.wallet.moudles.request.card.CreditCardPresenter;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 储蓄卡列表
 */
@Route(path = ActivityConstant.CREDIT_CARD)
public class CreditCardListActivity extends MyActivity implements CreditCardContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.add_saving_card)
    AutoRelativeLayout addSavingCard;

    CreditCardPresenter presenter;

    private CrediteCardAdapter adapter;

    List<CreditCard> creditCards;

    int RESULT_OK=200;

    @Autowired
    int quickPay;//交易类型
    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_list_card;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("选择信用卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setRightIcon(R.mipmap.choice_add_card);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    @Override
    protected void initData() {
        ARouter.getInstance().inject(this);
        presenter=new CreditCardPresenter(this,this);
        adapter=new CrediteCardAdapter(context);
        getCreditCard();
    }

    @Override
    public void onRightClick(View v) {
        startActivity(AddCreditCardActivity.class);
    }

    @OnClick(R.id.add_saving_card)
    public void onViewClicked() {
        startActivity(AddCreditCardActivity.class);
    }

    //获取信用卡列表
    public void getCreditCard(){
        ListBaseData listBaseData=new ListBaseData();
        listBaseData.setPageCurrent(currentPage);
        listBaseData.setPageSize(pageSize);
        presenter.queryCreditCard(getUserId(),listBaseData);
        adapter.setOnItemResultDataListener(new CrediteCardAdapter.OnItemResultDataListener() {
            @Override
            public void onResultData(int i) {
                if (quickPay==1){
                    CreditCard creditCard=creditCards.get(i);
                    Intent intent=new Intent();
                    intent.putExtra("creditCard",creditCard);
                    setResult(RESULT_OK,intent);
                    finish();
                }else {
                    return;
                }
            }
        });
        adapter.setOnItemModifyNickName(new CrediteCardAdapter.OnItemModifyNickName() {
            @Override
            public void bankId(String bankId) {
                new OtherDialog(context,1, bankId, false, new OtherDialog.OnNickNameListenter() {
                    @Override
                    public void setNickName(String str) {

                    }
                }).show();
            }
        });
    }


    @Override
    public void setCreditCard(List<CreditCard> list) {
        creditCards=list;
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setDebitCard(List<DebitCard> list) {

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }
}
