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
import com.minmai.wallet.moudles.ui.cash.CreditCardListActivity;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

/**
 * 信用卡适配器
 */
public class CrediteCardAdapter extends BaseRecyclerViewAdapter<CrediteCardAdapter.ViewHolder>{

    List<CreditCard> mData;
    private Context context;

    private OnItemResultDataListener onItemResultDataListener;
    private OnItemModifyNickName onItemModifyNickName;

    public CrediteCardAdapter(Context context) {
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
        viewHolder.tvBankNick.setText(mData.get(i).getCreditAlias());
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
        viewHolder.imgEditNick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemModifyNickName.bankId(mData.get(i).getCreditId());

            }
        });


    }

    //设置数据
    public void setData(List<CreditCard> data) {
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
            imgEditNick= (ImageView) findViewById(R.id.img_edit_nick);
            tvBankName= (TextView) findViewById(R.id.tv_bank_name);
            tvBankType= (TextView) findViewById(R.id.tv_bank_type);
            tvBankNo= (TextView) findViewById(R.id.tv_bank_no);
            tvBankNick= (TextView) findViewById(R.id.tv_bank_nick);
        }
    }

    //信用卡信息回掉的接口
    public interface OnItemResultDataListener{
        void onResultData(int i);
    }

    public void setOnItemResultDataListener(OnItemResultDataListener onItemResultDataListener){
        this.onItemResultDataListener=onItemResultDataListener;
    }

    public interface OnItemModifyNickName{
        void bankId(String bankId);
    }

    public void setOnItemModifyNickName(OnItemModifyNickName onItemModifyNickName){
        this.onItemModifyNickName=onItemModifyNickName;
    }



}
