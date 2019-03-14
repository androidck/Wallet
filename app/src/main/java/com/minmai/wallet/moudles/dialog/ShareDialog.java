package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.minmai.wallet.R;

/**
 * 分享dialog
 */
public class ShareDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public ShareDialog(Context context, boolean isCancelable) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_share);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        TextView tvWeiChat=findViewById(R.id.tv_weichat);
        TextView tvPenYou=findViewById(R.id.tv_penyou);
        TextView tvQq=findViewById(R.id.tv_qq);
        TextView tvZone=findViewById(R.id.tv_zone);
        TextView tvEsc=findViewById(R.id.tv_esc);
        tvWeiChat.setOnClickListener(this);
        tvPenYou.setOnClickListener(this);
        tvQq.setOnClickListener(this);
        tvZone.setOnClickListener(this);
        tvEsc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_weichat:
                break;
            case R.id.tv_penyou:
                break;
            case R.id.tv_qq:
                break;
            case R.id.tv_zone:
                break;
            case R.id.tv_esc:
                dismiss();
                break;

        }
    }


}
