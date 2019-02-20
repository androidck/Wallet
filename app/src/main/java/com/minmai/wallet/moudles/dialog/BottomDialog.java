package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.minmai.wallet.R;

import java.util.List;

/**
 * 底部选项卡dialog
 */
public class BottomDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private List<String> list;

    private RecyclerView.Adapter adapter;
    private int closeStatus;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)

    public BottomDialog(Context context){
        super(context);
        this.context=context;
    }

    public BottomDialog(Context context, boolean isCancelable, RecyclerView.Adapter adapter) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.adapter=adapter;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView(){
        TextView tvEsc=findViewById(R.id.tv_esc);
        tvEsc.setOnClickListener(this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
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

