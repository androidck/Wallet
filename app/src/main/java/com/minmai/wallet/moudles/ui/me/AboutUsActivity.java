package com.minmai.wallet.moudles.ui.me;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 关于我们
 */
public class AboutUsActivity extends MyActivity {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_opinion)
    TextView tvOpinion;
    @BindView(R.id.tv_check_update)
    TextView tvCheckUpdate;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("关于我们");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_opinion, R.id.tv_check_update})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_opinion:
                startActivity(OpinionActivity.class);
                break;
            case R.id.tv_check_update:
                toast("当前是最新版本，无需更新");
                break;
        }
    }
}
