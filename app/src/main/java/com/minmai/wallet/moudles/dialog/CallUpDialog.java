package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjq.widget.ClearEditText;
import com.minmai.wallet.R;

/**
 * 昵称
 */
public class CallUpDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private String phone;//电话号码


    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public CallUpDialog(Context context, boolean isCancelable,String phoneNo) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.phone=phoneNo;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_call_up);//这行一定要写在前面
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
        TextView tvPhoneNo=findViewById(R.id.tv_phone_number);
        tvEsc.setOnClickListener(this);
        tvOk.setOnClickListener(this);
        tvPhoneNo.setText(phone);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_esc:
                dismiss();
                break;
            case R.id.btn_ok:
                break;
        }
    }
}
