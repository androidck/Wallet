package com.minmai.wallet.moudles.ui.me;

import android.graphics.Color;
import android.view.View;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.adapter.BottomDialogAdapter;
import com.minmai.wallet.moudles.dialog.BottomDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class PersonalportraitActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.img_head)
    CircleImageView imgHead;

    List<String> list;

    private BottomDialogAdapter adapter;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_per_port;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("个人头像");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_white);
        tbLoginTitle.setBackgroundColor(Color.parseColor("#323232"));
        tbLoginTitle.setTitleColor(Color.parseColor("#ffffff"));
        tbLoginTitle.setRightIcon(R.mipmap.bar_icon_back_white);

        adapter=new BottomDialogAdapter(PersonalportraitActivity.this);
    }

    @Override
    public boolean statusBarDarkFont() {
        return false;
    }

    @Override
    protected void initData() {
        list=new ArrayList<>();
        list.add("拍照");
        list.add("从手机相册选择");
        list.add("保存图片");
        adapter.setData(list);
    }

    @Override
    public void onRightClick(View v) {
        new BottomDialog(PersonalportraitActivity.this,false,adapter).show();
    }


}
