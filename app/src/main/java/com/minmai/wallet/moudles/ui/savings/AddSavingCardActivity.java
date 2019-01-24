package com.minmai.wallet.moudles.ui.savings;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加储蓄卡
 */
public class AddSavingCardActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_bank_card)
    ImageView imgBankCard;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.bank_number)
    ClearEditText bankNumber;
    @BindView(R.id.tv_open_member)
    TextView tvOpenMember;
    @BindView(R.id.tv_open_address)
    TextView tvOpenAddress;
    @BindView(R.id.ed_phone)
    ClearEditText edPhone;
    @BindView(R.id.btn_commit)
    Button btnCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_saving;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("添加储蓄卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.img_bank_card, R.id.tv_open_member, R.id.tv_open_address, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_bank_card:
                break;
            case R.id.tv_open_member:
                break;
            case R.id.tv_open_address:
                break;
            case R.id.btn_commit:
                break;
        }
    }
}
