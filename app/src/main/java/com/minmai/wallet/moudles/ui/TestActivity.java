package com.minmai.wallet.moudles.ui;

import android.util.Log;
import android.widget.Button;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.bean.response.BannerInfo;
import com.minmai.wallet.moudles.bean.response.RollMessage;
import com.minmai.wallet.moudles.request.banner.BannerContract;
import com.minmai.wallet.moudles.request.banner.BannerPresenter;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TestActivity extends MyActivity implements BannerContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.btn_get_banner)
    Button btnGetBanner;

    BannerPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_tets;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        presenter=new BannerPresenter(this,this);
    }

    @Override
    protected void initData() {

    }



    @OnClick(R.id.btn_get_banner)
    public void onViewClicked() {
        presenter.getBannerList();
    }


    @Override
    public void setContent(List<BannerInfo> list) {
        Log.d("Databanninfo",list.toString());
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setRollMessage(List<RollMessage> list) {

    }
}
