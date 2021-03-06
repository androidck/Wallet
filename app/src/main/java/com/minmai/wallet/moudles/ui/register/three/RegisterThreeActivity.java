package com.minmai.wallet.moudles.ui.register.three;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.uitl.MD5;
import com.minmai.wallet.common.uitl.MD5Utils;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.moudles.bean.request.UserInfoReq;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.bean.response.UserInfo;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;
import com.minmai.wallet.moudles.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册第三部
 */
@Route(path = ActivityConstant.USER_REGISTER_THREE)
public class RegisterThreeActivity extends MyActivity implements UserContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;//标题
    @BindView(R.id.et_recommend_phone)
    ClearEditText etRecommendPhone;//手机号
    @BindView(R.id.tv_book)
    ImageView tvBook;//通讯录
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;//密码
    @BindView(R.id.is_check_read)
    CheckBox isCheckRead;//是否阅读协议
    @BindView(R.id.tv_clause)
    TextView tvClause;//协议
    @BindView(R.id.btn_register_commit)
    Button btnRegisterCommit;//注册

    private String recommendPhone;//推荐人手机号
    private String loginPwd;//登录密码

    @Autowired(name = "phone")
    String phone;//手机号
    @Autowired(name = "code")
    String code;//验证码
    @Autowired(name = "codeId")
    String codeId;//验证码id

    UserPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register_three;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        tbLoginTitle.setTitle(R.string.tv_register_title);
        ARouter.getInstance().inject(this);
        etRecommendPhone.addTextChangedListener(new PhoneTextWatcher(etRecommendPhone));
        presenter=new UserPresenter(this,this);
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        recommendPhone=etRecommendPhone.getText().toString().trim().replace(" ","");
        loginPwd=etLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(recommendPhone)){
            toast("推荐人手机号不能为空");
        }else if (!ValidateUtils.Mobile(recommendPhone)){
            toast("推荐码不正确");
        }else if (TextUtils.isEmpty(loginPwd)){
            toast("密码不能为空");
        }else if (!isCheckRead.isChecked()){
            toast("请先阅读和同意注册协议");
        }else {
            UserInfoReq userInfoReq=new UserInfoReq();
            userInfoReq.setLoginName(phone);
            userInfoReq.setPwd(MD5Utils.stringToMD5(loginPwd));
            userInfoReq.setCodeId(codeId);
            userInfoReq.setCode(code);
            userInfoReq.setRecommendCode(recommendPhone);
            presenter.userRegister(userInfoReq);
        }
    }

    @OnClick({R.id.tv_book, R.id.tv_clause, R.id.btn_register_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_book:
                break;
            case R.id.tv_clause:
                startBrowserActivity(this,MainActivity.MODE_SONIC,"https://m.baidu.com");
                break;
            case R.id.btn_register_commit:
                startRequestInterface();
                break;
        }
    }



    @Override
    public void Authentication(int registerState) {
        super.Authentication(registerState);
    }

    @Override
    public void onSetContent(Object object) {
        UserInfo userInfoResp = (UserInfo) object;
        Authentication(userInfoResp.getRegisterState());
        finish();
    }

    @Override
    public void onSetCodeId(String codeId) {

    }

    @Override
    public void onSuccess(String msg) {
        toast(msg);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }

    @Override
    public void setPerCenterInfo(PerCenterInfo perCenterInfo) {

    }


}
