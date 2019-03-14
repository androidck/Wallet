package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.greendao.DbBankInfoDao;
import com.minmai.wallet.common.greendao.DbCenterInfoDao;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.greendao.RegisterStateRespDao;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.CityResp;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 未登录提示的dialog
 */
public class LoginOutTipDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    DbUserInfoDao userInfoDao;
    DbCenterInfoDao centerInfoDao;
    DbBankInfoDao bankInfoDao;
    RegisterStateRespDao respDao;


    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public LoginOutTipDialog(Context context, boolean isCancelable) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_login_tip);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        centerInfoDao=MyApplication.getInstances().getDaoSession().getDbCenterInfoDao();
        bankInfoDao=MyApplication.getInstances().getDaoSession().getDbBankInfoDao();
        respDao=MyApplication.getInstances().getDaoSession().getRegisterStateRespDao();
        initView();
    }

    private void initView() {
        TextView tvEsc=findViewById(R.id.tv_esc);
        TextView tvLogin=findViewById(R.id.tv_login);
        TextView tvLoginOut=findViewById(R.id.tv_tip);
        tvEsc.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
        tvLogin.setText("退出");
        tvLoginOut.setText("确定要退出账号吗？");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_esc:
                dismiss();
                break;
            case R.id.tv_login:
                userLoginOut();
                userInfoDao.deleteAll();
                centerInfoDao.deleteAll();
                bankInfoDao.deleteAll();
                respDao.deleteAll();
                ARouter.getInstance().build(ActivityConstant.MAIN).navigation();
                ToastUtils.show("账号已退出");
                dismiss();
                break;
        }
    }

    //退出登录请求
    public void userLoginOut(){
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(null), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().userSignOut(currentTimeMillis,sign,userInfoDao.loadAll().get(0).getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {

                    }
                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError){
                            ToastUtils.show("网络连接失败，请检查网络");
                        }
                    }
                    @Override
                    protected void onError(BaseEntry<String> t) {
                        ToastUtils.show(t.getMsg());
                    }
                });
    }



}
