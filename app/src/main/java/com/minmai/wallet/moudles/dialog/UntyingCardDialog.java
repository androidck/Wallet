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
import com.minmai.wallet.common.greendao.DbCenterInfoDao;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 解绑信用卡dialog
 */
public class UntyingCardDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    DbUserInfoDao userInfoDao;
    DbCenterInfoDao centerInfoDao;
    private String bankId;
    private OnUntyingCardListener onUntyingCardListener;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public UntyingCardDialog(Context context, boolean isCancelable,String bankId,OnUntyingCardListener onUntyingCardListener) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.bankId=bankId;
        this.onUntyingCardListener=onUntyingCardListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_untrying_tip);//这行一定要写在前面
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
        tvLogin.setText("解绑");
        tvLoginOut.setText("信用卡解绑后，将无法正常使用业务，确定要解绑吗？");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_esc:
                dismiss();
                break;
            case R.id.tv_login:
                untyingBank(bankId);
                break;
        }
    }

    //解绑信用卡
    public void untyingBank(String bankId){
        String userId=userInfoDao.loadAll().get(0).getUserId();
        Map<String,String> map=new HashMap<>();
        map.put("id",bankId);
        long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
        String sign= TokenUtils.getSign(TokenUtils.objectMap(map), EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
        RetrofitUtil
                .getInstance()
                .initRetrofit().delCreditCard(currentTimeMillis,sign,userId,bankId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        onUntyingCardListener.onSuccess(t.getMsg());
                        dismiss();
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

    public interface OnUntyingCardListener{
        void onSuccess(String msg);
    }

}
