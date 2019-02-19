package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * 底部选项卡dialog
 */
public class BottomDialog extends Dialog implements View.OnClickListener {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    private List<String> list;
    BottomDialogAdapter adapter;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public BottomDialog(Context context, boolean isCancelable, List<String> list) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.list=list;
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
        adapter=new BottomDialogAdapter(context);
        adapter.setData(list);
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

    public static class BottomDialogAdapter extends BaseRecyclerViewAdapter<BottomDialogAdapter.ViewHolder>{
        List<String> menuName;
        public BottomDialogAdapter(Context context) {
            super(context);
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            return new ViewHolder(viewGroup, R.layout.item_buttom_dialog);
        }
        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.tvItemName.setText(getItem(i));
        }

        public String getItem(int position) {
            return menuName.get(position);
        }
        @Override
        public int getItemCount() {
            return menuName == null ? 0 : menuName.size();
        }

        public void setData(List<String> data) {
            menuName = data;
            notifyDataSetChanged();
        }
        class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
            AutoLinearLayout itemName;
            TextView tvItemName;
            public ViewHolder(ViewGroup parent, int layoutId) {
                super(parent, layoutId);
                itemName= (AutoLinearLayout) findViewById(R.id.item_menu);
                tvItemName= (TextView) findViewById(R.id.item_tv_name);
            }
        }
    }


}

