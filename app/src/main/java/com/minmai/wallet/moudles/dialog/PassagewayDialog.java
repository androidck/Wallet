package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.minmai.wallet.R;

/**
 * 通道选择dialog
 */
public class PassagewayDialog extends Dialog implements View.OnClickListener{

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private RecyclerView.Adapter adapter;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public PassagewayDialog(Context context, boolean isCancelable) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_passageway);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    //初始化页面
    private void initView() {
        //取消
        Button btnEsc=findViewById(R.id.btn_esc);
        //确定
        Button btnOk=findViewById(R.id.btn_ok);
        btnEsc.setOnClickListener(this);
        btnOk.setOnClickListener(this);
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
