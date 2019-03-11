package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.uitl.HideDataUtil;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.moudles.bean.response.CreditCard;
import com.minmai.wallet.moudles.bean.response.DebitCard;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * 信用卡适配器
 */
public class DebitCardAdapter extends BaseRecyclerViewAdapter<DebitCardAdapter.ViewHolder>{

    List<DebitCard> mData;
    private Context context;

    private OnItemResultDataListener onItemResultDataListener;

    public DebitCardAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup,R.layout.item_quick_card);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.tvBankName.setText(mData.get(i).getBankName());
        viewHolder.tvBankType.setText(mData.get(i).getType());
        viewHolder.tvBankNo.setText(HideDataUtil.formatCarNo(mData.get(i).getCarNumber()));
        Glide.with(context).load(mData.get(i).getLogo()).into(viewHolder.imgBank);
        Glide.with(context).load(mData.get(i).getBankBackground()).into(viewHolder.imgBigLogo);
        viewHolder.lyBg.setBackground(ViewUtil.setDrawable(mData.get(i).getBackgroundColor()));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemResultDataListener.onResultData(i);
            }
        });
        viewHolder.imgEditNick.setVisibility(View.GONE);
    }

    //设置数据
    public void setData(List<DebitCard> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        AutoLinearLayout lyBg;
        ImageView imgBank,imgBigLogo,imgEditNick;
        TextView tvBankName,tvBankType,tvBankNo,tvBankNick;
        AutoRelativeLayout lyBankBg;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            lyBg= (AutoLinearLayout) findViewById(R.id.tv_card_bg);
            lyBankBg= (AutoRelativeLayout) findViewById(R.id.ly_bank_bg);
            imgBank= (ImageView) findViewById(R.id.bank_img);
            imgBigLogo= (ImageView) findViewById(R.id.img_big_logo);
            tvBankName= (TextView) findViewById(R.id.tv_bank_name);
            tvBankType= (TextView) findViewById(R.id.tv_bank_type);
            tvBankNo= (TextView) findViewById(R.id.tv_bank_no);
            tvBankNick= (TextView) findViewById(R.id.tv_bank_nick);
            imgEditNick= (ImageView) findViewById(R.id.img_edit_nick);
        }
    }

    //信用卡信息回掉的接口
    public interface OnItemResultDataListener{
        void onResultData(int i);
    }

    public void setOnItemResultDataListener(OnItemResultDataListener onItemResultDataListener){
        this.onItemResultDataListener=onItemResultDataListener;
    }




}
