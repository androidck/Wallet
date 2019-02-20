package com.minmai.wallet.moudles.ui.me;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.adapter.BottomDialogAdapter;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 管理账号
 */
public class ManageAcountActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.ly_change_phone)
    AutoLinearLayout lyChangePhone;
    @BindView(R.id.tv_update_password)
    TextView tvUpdatePassword;
    @BindView(R.id.tv_withdraw_pass)
    TextView tvWithdrawPass;

    List<String> list;
    private BottomDialogAdapter adapter;
    BottomDialog bottomDialog;



    @Override
    protected int getLayoutId() {
        return R.layout.activity_manage_acount;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
        tbLoginTitle.setTitle("管理账户");
        tbLoginTitle.setBackgroundColor(Color.parseColor("#ffffff"));
        adapter=new BottomDialogAdapter(ManageAcountActivity.this);
    }

    @Override
    protected void initData() {
        list=new ArrayList<>();
        list.add("更换手机号");
        adapter.setData(list);
        adapter.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                toast(list.get(position));
                bottomDialog.dismiss();
            }
        });
    }



    @OnClick({R.id.ly_change_phone, R.id.tv_update_password, R.id.tv_withdraw_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ly_change_phone:
                bottomDialog= new BottomDialog(ManageAcountActivity.this,false,adapter);
                bottomDialog.show();
                break;
            case R.id.tv_update_password:
                startActivity(UpdateUserPassActivity.class);
                break;
            case R.id.tv_withdraw_pass:
                startActivity(SetupPasswordActivity.class);
                break;
        }
    }
}
