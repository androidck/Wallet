package com.minmai.wallet.moudles.ui.identity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 完善信息第一步
 */
public class IdentifyOneActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_font)
    ImageView imgFont;//身份证正面
    @BindView(R.id.ed_real_name)
    ClearEditText edRealName;//真实姓名
    @BindView(R.id.ed_id_no)
    ClearEditText edIdNo;//身份证号
    @BindView(R.id.img_back)
    ImageView imgBack;//身份证反面
    @BindView(R.id.ed_validity)
    ClearEditText edValidity;//
    @BindView(R.id.btn_next)
    Button btnNext;//提交

    @Override
    protected int getLayoutId() {
        return R.layout.activity_identity_one;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("完善信息");
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.img_font, R.id.img_back, R.id.btn_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_font:
                break;
            case R.id.img_back:
                break;
            case R.id.btn_next:
                break;
        }
    }
}
