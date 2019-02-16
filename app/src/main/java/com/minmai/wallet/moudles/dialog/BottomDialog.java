package com.minmai.wallet.moudles.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.minmai.wallet.R;

import java.util.List;

/**
 * 底部选项卡dialog
 */
public class BottomDialog extends Dialog implements View.OnClickListener {

    private Activity mContext;

    private List<String> list;

    public BottomDialog(@NonNull Activity context, List<String> list) {
        super(context);
        this.list=list;
        this.mContext=context;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom);
        setCanceledOnTouchOutside(false);
        initView();
        /*设置窗口弹出动画*/
        getWindow().setWindowAnimations(R.style.ActionSheetDialogAnimation);
        //全屏处理
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        WindowManager wm = mContext.getWindowManager();
        /*设置宽度*/
        lp.width = wm.getDefaultDisplay().getWidth();
        getWindow().setAttributes(lp);
    }

    private void initView(){
        //列表
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        //取消
        TextView tvEsc=findViewById(R.id.tv_esc);
        tvEsc.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_esc:
                dismiss();
                break;
        }
    }
}
