package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjq.toast.ToastUtils;
import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;

/**
 * 昵称
 */
public class NickNameDialog extends Dialog implements View.OnClickListener,UserContract.View {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;

    private String nickName;
    UserPresenter presenter;
    DbUserInfoDao userInfoDao;
    ClearEditText editText;
    private OnNickNameListenter onNickNameListenter;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public NickNameDialog(Context context, String nickName, boolean isCancelable,OnNickNameListenter onNickNameListenter) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.nickName=nickName;
        this.onNickNameListenter=onNickNameListenter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_nickname);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        TextView tvEsc=findViewById(R.id.btn_esc);
        TextView tvOk=findViewById(R.id.btn_ok);
        editText=findViewById(R.id.ed_nickname);
        tvEsc.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        presenter=new UserPresenter(context,this);
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        if (nickName.length()==0||nickName.equals("")||nickName==null){
            editText.setHint("请输入昵称");
        }else {
            editText.setHint(nickName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_esc:
                dismiss();
                break;
            case R.id.btn_ok:
                updateNickName();
                dismiss();
                break;
        }
    }

    //修改昵称
    public void updateNickName(){
        if (editText.getText().toString().trim().length()==0){
            ToastUtils.show("昵称不能为空");
        }else {
            String userId=userInfoDao.loadAll().get(0).getUserId();
            presenter.updateUserNiceName(userId,editText.getText().toString().trim());
            onNickNameListenter.setNickName(editText.getText().toString().trim());
        }

    }

    @Override
    public void onSetContent(Object object) {

    }

    @Override
    public void onSetCodeId(String codeId) {

    }

    @Override
    public void onSuccess(String msg) {
        if ("访问成功".equals(msg)){
            ToastUtils.show("修改成功");
        }
    }

    @Override
    public void fail(String msg) {

    }

    @Override
    public void setPerCenterInfo(PerCenterInfo perCenterInfo) {

    }

    public interface OnNickNameListenter{
        void setNickName(String str);
    }

    public void setOnNickNameListenter(OnNickNameListenter onNickNameListenter){
        this.onNickNameListenter=onNickNameListenter;
    }


}
