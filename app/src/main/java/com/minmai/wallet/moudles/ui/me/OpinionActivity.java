package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.request.user.FeedBackContract;
import com.minmai.wallet.moudles.request.user.FeedBackPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 意见反馈
 */
public class OpinionActivity extends MyActivity implements FeedBackContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.ed_opinion)
    EditText edOpinion;
    @BindView(R.id.ed_phone)
    EditText edPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;
    FeedBackPresenter presenter;

    String opinion;
    String phone;
    String content;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_opinion;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("意见反馈");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {
        presenter=new FeedBackPresenter(this,this);
    }

    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        opinion=edOpinion.getText().toString().trim();
        phone=edPhone.getText().toString().trim();
        if (TextUtils.isEmpty(opinion)){
            toast("反馈内容不能为空");
        }else {
            if(TextUtils.isEmpty(phone)){
                content=opinion;
            }else {
                content=opinion+","+phone;
            }
            presenter.userFeedBack(getUserId(),content);
        }

    }

    @OnClick(R.id.btn_commit)
    public void onViewClicked() {
        startRequestInterface();
    }

    @Override
    public void onSuccess(String msg) {
        if (msg.equals("访问成功")){
            toast("意见已反馈");
        }
        finish();
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }
}
