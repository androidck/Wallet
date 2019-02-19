package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

public class BottomDialogAdapter extends BaseRecyclerViewAdapter<BottomDialogAdapter.ViewHolder> {

    List<String> menuName;

    private OnItemClickListener onItemClickListener;
    private OnItemDismissListener onItemDismissListener;

    public BottomDialogAdapter(Context context) {
            super(context);
        }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_buttom_dialog);
    }
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvItemName.setText(getItem(i));
        viewHolder.tvItemName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onClick(i);
            }
        });
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

    //item 点击事件
    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void  setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    public interface OnItemDismissListener{
        void disMissClick();
    }

    public void setOnItemDismissListener(OnItemDismissListener onItemDismissListener){
        this.onItemDismissListener=onItemDismissListener;
    }

}
