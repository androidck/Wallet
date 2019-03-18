package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;

/**
 *  分润适配器
 */
public class ShareMoistAdapter  extends BaseRecyclerViewAdapter<ShareMoistAdapter.ViewHolder>{

    private Context mContext;

    public ShareMoistAdapter(Context context) {
        super(context);
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_share_moist);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        TextView tvPhone,tvType,tvDate,tvConsumption,tvShareMoist;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            tvPhone= (TextView) findViewById(R.id.tv_phone);
            tvType= (TextView) findViewById(R.id.tv_type);
            tvDate= (TextView) findViewById(R.id.tv_date);
            tvConsumption= (TextView) findViewById(R.id.tv_consumption);
            tvShareMoist= (TextView) findViewById(R.id.tv_share_moist);
        }
    }
}
