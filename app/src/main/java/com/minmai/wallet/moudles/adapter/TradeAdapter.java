package com.minmai.wallet.moudles.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.moudles.bean.response.Trade;

import java.util.List;

/**
 * 快捷交易记录 适配器
 */
public class TradeAdapter extends  BaseRecyclerViewAdapter<TradeAdapter.ViewHolder>{

    private List<Trade> mData;
    private Context context;

    public TradeAdapter(Context context) {
        super(context);
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup,R.layout.item_trade);
    }

    /**
     * consumptiongSeq : 6181129164918256
     * tradingAmount : 1561.0000
     * creditId : 00e53c600d36476a90bae375e29801cf
     * creditCard : 6253360019014819
     * tradingStatus : 3
     * tradingDate : 2018-11-29 16:49:18.0
     * bankName : 农业银行
     */

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvBankName.setText(mData.get(i).getBankName()+"（"+TextUtil.subCardNo(mData.get(i).getCreditCard())+"）");
        viewHolder.tvTradeMoney.setText(TextUtil.format2(Double.valueOf(mData.get(i).getTradingAmount())));
        viewHolder.tvTradeChannel.setText(mData.get(i).getChannelName());
        viewHolder.tvTradeTime.setText(mData.get(i).getTradingDate());
        if (mData.get(i).getTradingStatus().equals("1")){
            viewHolder.tvTradeStatus.setText("交易成功");
            viewHolder.tvTradeStatus.setTextColor(R.color.tradeSuccess);
        }else if (mData.get(i).getTradingStatus().equals("2")){
            viewHolder.tvTradeStatus.setText("交易失败");
        }else if (mData.get(i).getTradingStatus().equals("3")){
            viewHolder.tvTradeStatus.setTextColor(R.color.tradeLoading);
            viewHolder.tvTradeStatus.setText("交易处理中");
        }
    }

    public void setData(List<Trade> list){
        mData=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData==null?0:mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{

        TextView tvBankName,tvTradeMoney,tvTradeChannel,tvTradeTime,tvTradeStatus;

        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            tvBankName= (TextView) findViewById(R.id.tv_bank_name);
            tvTradeMoney= (TextView) findViewById(R.id.tv_trade_money);
            tvTradeChannel= (TextView) findViewById(R.id.tv_trade_channel);
            tvTradeTime= (TextView) findViewById(R.id.tv_trade_time);
            tvTradeStatus= (TextView) findViewById(R.id.tv_trade_status);
        }
    }
}
