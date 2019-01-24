package com.minmai.wallet.moudles.ui.cash;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.view.MyTextWatcher;
import com.minmai.wallet.common.view.PhoneTextWatcher;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 添加信用卡
 */
public class AddCreditCardActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_photograph)
    ImageButton imgPhotograph;
    @BindView(R.id.tv_phone)
    ClearEditText tvPhone;
    @BindView(R.id.tv_bill_date)
    TextView tvBillDate;
    @BindView(R.id.tv_repayment_date)
    TextView tvRepaymentDate;
    @BindView(R.id.bank_img)
    ImageView bankImg;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_type)
    TextView tvBankType;
    @BindView(R.id.ed_credit_number)
    ClearEditText edCreditNumber;
    @BindView(R.id.ed_validity)
    ClearEditText edValidity;
    @BindView(R.id.ed_cvv2)
    ClearEditText edCvv2;
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_credit_card;
    }


    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tvPhone.addTextChangedListener(new PhoneTextWatcher(tvPhone));
        tbLoginTitle.setTitle("添加信用卡");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_photograph, R.id.tv_bill_date, R.id.tv_repayment_date})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_photograph:
                break;
            case R.id.tv_bill_date:
                break;
            case R.id.tv_repayment_date:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
