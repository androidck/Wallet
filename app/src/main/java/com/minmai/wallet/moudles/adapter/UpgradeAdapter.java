package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.moudles.bean.response.PurchaseMemberEntity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

public class UpgradeAdapter extends BaseRecyclerViewAdapter<UpgradeAdapter.ViewHolder> {

    private List<PurchaseMemberEntity> mData;
    private Context context;
    private OnPurchaseListener onPurchaseListener;

    public UpgradeAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_upgrade);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final String str=String.format("%.2f",new Double(mData.get(i).getPrice()).doubleValue());
        viewHolder.tvGrade.setText(mData.get(i).getLevelName());
        viewHolder.lyUpGradeBg.setBackground(ViewUtil.setDrawable(mData.get(i).getBackgroundColor()));
        viewHolder.tvPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPurchaseListener.onItemPay(mData.get(i).getLevelId(),str);
            }
        });

        if (str.equals("0.00")){
            viewHolder.tvIntroduce.setText(mData.get(i).getIntroduce());
            viewHolder.lyPay.setVisibility(View.GONE);
        }else {
            viewHolder.tvIntroduce.setText("支付"+str+"元，"+mData.get(i).getIntroduce());
        }
    }

    public void setData(List<PurchaseMemberEntity> list){
        this.mData=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        TextView tvGrade,tvIntroduce,tvPay;
        AutoRelativeLayout lyPay;
        AutoLinearLayout lyUpGradeBg;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            tvGrade= (TextView) findViewById(R.id.tv_grade);
            tvIntroduce= (TextView) findViewById(R.id.tv_introduce);
            tvPay= (TextView) findViewById(R.id.tv_pay);
            lyPay= (AutoRelativeLayout) findViewById(R.id.ly_pay);
            lyUpGradeBg= (AutoLinearLayout) findViewById(R.id.ly_upgrade_bg);
        }
    }

    public interface OnPurchaseListener{
        void onItemPay(String gradeId,String money);
    }

    public void setOnPurchaseListener(OnPurchaseListener onPurchaseListener) {
        this.onPurchaseListener = onPurchaseListener;
    }
}
