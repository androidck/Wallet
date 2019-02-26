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
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.greendao.DbCenterInfoDao;
import com.minmai.wallet.common.greendao.DbUserInfoDao;

/**
 * 未登录提示的dialog
 */
public class LoginOutTipDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    DbUserInfoDao userInfoDao;
    DbCenterInfoDao centerInfoDao;
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
                userInfoDao.deleteAll();
                centerInfoDao.deleteAll();
                ARouter.getInstance().build(ActivityConstant.MAIN).navigation();
                ToastUtils.show("账号已退出");
                dismiss();
                break;
        }
    }
}
