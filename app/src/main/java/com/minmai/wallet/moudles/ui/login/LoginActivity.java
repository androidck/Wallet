package com.minmai.wallet.moudles.ui.login;

import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.bar.TitleBar;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.enumcode.EnumCodeUse;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.uitl.MD5;
import com.minmai.wallet.common.uitl.ValidateUtils;
import com.minmai.wallet.common.view.PhoneTextWatcher;
import com.minmai.wallet.moudles.bean.request.UserInfoReq;
import com.minmai.wallet.moudles.bean.response.UserInfo;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.request.UserContract;
import com.minmai.wallet.moudles.request.UserPresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 用户登录页面
 */
@Route(path = ActivityConstant.USER_LOGIN)
public class LoginActivity extends MyActivity implements UserContract.View {
    @BindView(R.id.tb_login_title)
    TitleBar tbLoginTitle;//标题
    @BindView(R.id.et_login_phone)
    ClearEditText etLoginPhone;//手机号输入框
    @BindView(R.id.et_login_password)
    ClearEditText etLoginPassword;//密码输入框
    @BindView(R.id.tv_login_forget)
    TextView tvLoginForget;//忘记密码
    @BindView(R.id.btn_login_commit)
    Button btnLoginCommit;//登录
    @BindView(R.id.tv_register)
    TextView tvRegister;//注册
    @BindView(R.id.tv_msg_login)
    TextView tvMsgLogin;//短信登录
    @BindView(R.id.btn_weixin_login)
    ImageView btnWeixinLogin;
    @BindView(R.id.tv_get_code)
    TextView tvGetCode;

    private int loginType = 1;//登录类型

    UserPresenter presenter;
    String phone;
    String loginPwd;
    String codeId;
    TimeCount timeCount;
    DbUserInfoDao userInfoDao;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected int getTitleBarId() {
        return R.id.tb_login_title;
    }

    @Override
    protected void initView() {
        etLoginPhone.addTextChangedListener(new PhoneTextWatcher(etLoginPhone));
        tbLoginTitle.setTitle("登录");
        timeCount = new TimeCount(10000, 1000,tvGetCode);
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
    }

    @Override
    protected void initData() {
        presenter=new UserPresenter(this,this);
    }


    @OnClick({R.id.tv_login_forget, R.id.btn_login_commit, R.id.tv_register, R.id.tv_msg_login, R.id.btn_weixin_login,R.id.tv_get_code})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_login_forget:
                break;
            case R.id.btn_login_commit:
                startRequestInterface();
                break;
            case R.id.tv_register:
                ARouter.getInstance().build(ActivityConstant.USER_REGISTER_ONE).navigation();
                break;
            case R.id.tv_msg_login:
                setLoginType();
                break;
            case R.id.btn_weixin_login:
                toast("正在开发中.....");
                break;
            case R.id.tv_get_code:
                phone=etLoginPhone.getText().toString().trim().replace(" ","");
                loginPwd=etLoginPassword.getText().toString().trim();
                if (TextUtils.isEmpty(phone)){
                    toast("手机号不能为空");
                }else if (!ValidateUtils.Mobile(phone)){
                    toast("手机号格式不正确");
                }else{
                    //发送验证码
                    presenter.bindSendCode(phone, EnumCodeUse.getEnumCodeUse(R.string.login));
                    timeCount.start();
                }
                break;
        }
    }


    @Override
    protected void startRequestInterface() {
        super.startRequestInterface();
        phone=etLoginPhone.getText().toString().trim().replace(" ","");
        loginPwd=etLoginPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            toast("手机号不能为空");
        }else if (!ValidateUtils.Mobile(phone)){
            toast("手机号格式不正确");
        }else if (loginType==1&&TextUtils.isEmpty(loginPwd)){
            toast("密码不能为空");
        }else if (loginType==2&&TextUtils.isEmpty(loginPwd)){
            toast("验证码不能为空");
        }else if (loginType==1){
            UserInfoReq userInfoResp =new UserInfoReq();
            userInfoResp.setLoginName(phone);
            userInfoResp.setPwd(MD5.md5Str(loginPwd));
            presenter.userPwdLogin(userInfoResp);
        }else if (loginType==2){
            UserInfoReq userInfoReq=new UserInfoReq();
            userInfoReq.setCode(loginPwd);
            userInfoReq.setPhone(phone);
            userInfoReq.setCodeId(codeId);
            presenter.userPhoneLogin(userInfoReq);
        }
    }

    //设置登录
    private void setLoginType() {
        if (loginType == 1) {
            loginType = 2;
            etLoginPassword.setHint("请输入验证码");
            tvMsgLogin.setText("密码登录");
            etLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            etLoginPassword.setSelection(etLoginPassword.getText().toString().length());
            etLoginPassword.setInputType(InputType.TYPE_CLASS_NUMBER);
            tvGetCode.setVisibility(View.VISIBLE);
        } else {
            loginType = 1;
            etLoginPassword.setHint("请输入密码");
            tvMsgLogin.setText("验证码登录");
            tvGetCode.setVisibility(View.GONE);
            etLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            etLoginPassword.setSelection(etLoginPassword.getText().toString().length());
            etLoginPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }
    }



    @Override
    public void onSetContent(Object object) {
        UserInfo userInfoResp = (UserInfo) object;

        //登录成功保存数据
        DbUserInfo userInfo=new DbUserInfo(null,userInfoResp.getId());
        userInfoDao.insert(userInfo);
        Authentication(userInfoResp.getRegisterState());
    }

    @Override
    public void onSetCodeId(String codeId) {
        this.codeId=codeId;
    }


    @Override
    public void onSuccess(String msg) {
        toast(msg);
    }

    @Override
    public void fail(String msg) {
        toast(msg);
    }
}
