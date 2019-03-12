package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.moudles.bean.response.RollMessage;

import java.util.List;

/**
 * 公告适配器
 */
public class NoticeAdapter extends BaseRecyclerViewAdapter<NoticeAdapter.ViewHolder> {

    private List<RollMessage> list;
    private Context mContext;

    public NoticeAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_notice);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvDateTime.setText(list.get(i).getCreateDate());
        viewHolder.tvContent.setText(list.get(i).getMessage());
    }

    public void setData(List<RollMessage> list){
        this.list=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        TextView tvDateTime,tvContent;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            tvContent= (TextView) findViewById(R.id.tv_content);
            tvDateTime= (TextView) findViewById(R.id.tv_date_time);
        }
    }
}
