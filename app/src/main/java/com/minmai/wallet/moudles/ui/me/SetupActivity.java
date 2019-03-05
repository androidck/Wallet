package com.minmai.wallet.moudles.ui.me;

import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hjq.bar.TitleBar;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.moudles.db.DbCenterInfo;
import com.minmai.wallet.moudles.dialog.LoginOutTipDialog;
import com.minmai.wallet.moudles.request.user.FeedBackContract;
import com.minmai.wallet.moudles.request.user.FeedBackPresenter;
import com.zhy.autolayout.AutoLinearLayout;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更多设置
 */
public class SetupActivity extends MyActivity implements FeedBackContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;
    @BindView(R.id.tv_account_security)
    TextView tvAccountSecurity;
    @BindView(R.id.tv_abount_me)
    TextView tvAbountMe;
    @BindView(R.id.tv_clean_cache)
    AutoLinearLayout tvCleanCache;
    @BindView(R.id.btn_login_out)
    Button btnLoginOut;
    @BindView(R.id.sw_switch)
    SwitchCompat swSwitch;

    FeedBackPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_setup;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle("更多设置");
        tbLoginTitle.setLeftIcon(R.mipmap.bar_icon_back_black);
    }

    @Override
    protected void initData() {
        presenter=new FeedBackPresenter(this,this);
        if (isShowPhone().equals("1")){
            swSwitch.setChecked(true);
        }else {
            swSwitch.setChecked(false);
        }
        //滑动按钮监听
        swSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    showHidePhone("1");
                    updateExtendOne("1");
                }else if (isChecked==false){
                    showHidePhone("2");
                    updateExtendOne("2");
                }
            }
        });
    }


    @OnClick({R.id.tv_account_security, R.id.tv_clean_cache, R.id.btn_login_out, R.id.tv_abount_me})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_account_security:
                startActivity(ManageAcountActivity.class);
                break;
            case R.id.tv_clean_cache:
                break;
            case R.id.btn_login_out:
                new LoginOutTipDialog(SetupActivity.this, false).show();
                break;
            case R.id.tv_abount_me:
                startActivity(AboutUsActivity.class);
                break;
        }
    }

    //隐藏显示手机号
    public void showHidePhone(String extendOne){
        presenter.updateExtendOne(getUserId(),extendOne);
    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    /**
     * 修改本地保存数据
     * @param extendOne
     */
    public void updateExtendOne(String extendOne){
        Long id=centerInfoDao.loadAll().get(0).getId();
        DbCenterInfo centerInfo=centerInfoDao.load(id);
        centerInfo.setExtendOne(extendOne);
        centerInfoDao.update(centerInfo);
    }
}
