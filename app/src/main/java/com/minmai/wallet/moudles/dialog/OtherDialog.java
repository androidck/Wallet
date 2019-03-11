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
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.enumcode.EnumService;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.SystemUtil;
import com.minmai.wallet.common.uitl.TokenUtils;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * 昵称
 */
public class OtherDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;

    private String nickName;
    public DbUserInfoDao userInfoDao;
    ClearEditText editText;
    private OnNickNameListenter onNickNameListenter;
    private int type;//表示修改什么的昵称
    private String creditId;//信用卡id



    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public OtherDialog(Context context, int type,String creditId, boolean isCancelable, OnNickNameListenter onNickNameListenter) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.type=type;
        this.creditId=creditId;
        this.iscancelable = isCancelable;
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
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        /*if (nickName.length()==0||nickName.equals("")||nickName==null){
            editText.setHint("请输入昵称");
        }else {
            editText.setHint(nickName);
        }*/
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_esc:
                dismiss();
                break;
            case R.id.btn_ok:
                clickCommit();
                break;
        }
    }


    //根据类型修改
    public void clickCommit(){
        if (type==1){
            modifyBankNick();
        }
    }

    //修改银行卡别称
    public void modifyBankNick(){
        nickName=editText.getText().toString().trim();
        if (nickName.length()==0||nickName.equals("")||nickName==null){
            ToastUtils.show("请输入昵称");
        }else {
            Map<String,String> map=new HashMap<>();
            map.put("id",creditId);
            map.put("creditAlias",nickName);
            long currentTimeMillis = SystemUtil.getInstance().getCurrentTimeMillis();
            String sign=TokenUtils.getSign(TokenUtils.objectMap(map),EnumService.getEnumServiceByServiceName(1),currentTimeMillis);
            RetrofitUtil
                    .getInstance()
                    .initRetrofit().updateCreditAlias(currentTimeMillis,sign,getUserId(),creditId,nickName)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new BaseObserver<String>(context,MainUtil.loadTxt) {
                        @Override
                        protected void onSuccess(BaseEntry<String> t) throws Exception {
                            dismiss();
                            ToastUtils.show(t.getMsg());
                            onNickNameListenter.setNickName(nickName);
                        }
                        @Override
                        protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                            if (isNetWorkError){
                                ToastUtils.show("网络错误请联系管理员");
                            }
                        }
                        @Override
                        protected void onError(BaseEntry<String> t) {
                            ToastUtils.show(t.getMsg());
                        }
                    });
        }

    }
    public interface OnNickNameListenter{
        void setNickName(String str);
    }

    public void setOnNickNameListenter(OnNickNameListenter onNickNameListenter){
        this.onNickNameListenter=onNickNameListenter;
    }


    //获取用户id
    public String getUserId(){
        return userInfoDao.loadAll().get(0).getUserId();
    }

}
