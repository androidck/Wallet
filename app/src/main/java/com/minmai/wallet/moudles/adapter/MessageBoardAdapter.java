package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.moudles.bean.response.ListLeaving;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 留言板适配器
 */
public class MessageBoardAdapter extends BaseRecyclerViewAdapter<MessageBoardAdapter.ViewHolder>{

    private List<ListLeaving> mData;
    private Context context;

    public MessageBoardAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_leaving_msg);
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    public void setData(List<ListLeaving> list){
       this.mData=list;
       notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(mData.get(i).getUserHead()).into(viewHolder.imgHead);
        viewHolder.tvContent.setText(mData.get(i).getLeaveMessageContent());
        viewHolder.tvPhone.setText(mData.get(i).getPhone());
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        CircleImageView imgHead;
        TextView tvPhone,tvContent,tvIsRead;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            imgHead= (CircleImageView) findViewById(R.id.img_head);
            tvPhone= (TextView) findViewById(R.id.tv_phone);
            tvContent= (TextView) findViewById(R.id.tv_content);
            tvIsRead= (TextView) findViewById(R.id.tv_is_read);
        }
    }
}
