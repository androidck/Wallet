package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hjq.base.BaseRecyclerViewAdapter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.uitl.TextUtil;
import com.minmai.wallet.moudles.bean.response.Channel;
import com.zhy.autolayout.AutoLinearLayout;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 通道列表适配器
 */
public class ChannelAdapter extends BaseRecyclerViewAdapter<ChannelAdapter.ViewHolder>{

    private Context context;
    private List<Channel> mData;
    Channel channel;
    int currentNum=-1;

    public OnItemCheckClickListener onItemCheckClickListener;

    public ChannelAdapter(Context context) {
        super(context);
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup, R.layout.item_channel);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.tvChannelName.setText(mData.get(i).getChannelName());
        DecimalFormat df2 =new DecimalFormat("#.00");
        String str2 =df2.format(mData.get(i).getFee());
        viewHolder.tvMerType.setText("商户类型："+mData.get(i).getExtendedField1()+"\t\t单笔手续费："+str2 +"元");
        viewHolder.tvSupportBank.setText("支持银行："+mData.get(i).getSupportBanks());
        if (mData.get(i).getScoreFlag().equals("1")){
            viewHolder.tvRate.setText("费率："+mData.get(i).getRate()+"%\t有积分");
        }else if (mData.get(i).getScoreFlag().equals("2")){
            viewHolder.tvRate.setText("费率："+mData.get(i).getRate()+"%\t无积分");
        }
        if (mData.get(i).isCheck()==false){
            viewHolder.imgCheck.setImageResource(R.mipmap.channel);
        }else {
            viewHolder.imgCheck.setImageResource(R.mipmap.channel_active);
        }
        viewHolder.lyItemChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentNum == -1){ //选中
                    mData.get(i).setCheck(true);
                    currentNum = i;
                    viewHolder.imgCheck.setImageResource(R.mipmap.channel_active);
                }else if(currentNum == i){ //同一个item选中变未选中
                    for(Channel person : mData){
                        person.setCheck(false);
                    }
                    viewHolder.imgCheck.setImageResource(R.mipmap.channel);
                    currentNum = -1;
                }else if(currentNum != i){ //不是同一个item选中当前的，去除上一个选中的
                    for(Channel person : mData){
                        person.setCheck(false);
                    }
                    viewHolder.imgCheck.setImageResource(R.mipmap.channel);
                    mData.get(i).setCheck(true);
                    currentNum = i;
                }
                notifyDataSetChanged();//刷新adapter
                channel=new Channel();
                channel.setChannelId(mData.get(currentNum).getChannelId());//通道id
                channel.setChannelName(mData.get(currentNum).getChannelName());//通道名称
                channel.setRate(mData.get(currentNum).getRate());//通道费率
                channel.setFee(mData.get(currentNum).getFee());//通道手续费
                channel.setSupportBanks(mData.get(currentNum).getSupportBanks());//通道支持银行
                channel.setScoreFlag(mData.get(currentNum).getScoreFlag());//是否有积分
                channel.setExtendedField1(mData.get(currentNum).getExtendedField1());//商户类型
                channel.setChannelSingleLimitUp(mData.get(currentNum).getChannelSingleLimitUp());
                channel.setChannelType(mData.get(currentNum).getChannelType());
                onItemCheckClickListener.onCheck(currentNum,channel);
            }
        });
      /*  viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channel=new Channel();
                channel.setChannelId(mData.get(currentNum).getChannelId());//通道id
                channel.setChannelName(mData.get(currentNum).getChannelName());//通道名称
                channel.setRate(mData.get(currentNum).getRate());//通道费率
                channel.setFee(mData.get(currentNum).getFee());//通道手续费
                channel.setSupportBanks(mData.get(currentNum).getSupportBanks());//通道支持银行
                channel.setScoreFlag(mData.get(currentNum).getScoreFlag());//是否有积分
                channel.setExtendedField1(mData.get(currentNum).getExtendedField1());//商户类型
                onItemCheckClickListener.onCheck(currentNum,channel);
            }
        });*/

    }

    public void setData(List<Channel> list){
        this.mData=list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        TextView tvChannelName,tvRate,tvMerType,tvSupportBank;
        ImageView imgCheck;
        AutoLinearLayout lyItemChannel;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            tvChannelName= (TextView) findViewById(R.id.tv_channel_name);
            tvRate= (TextView) findViewById(R.id.tv_rate);
            tvMerType= (TextView) findViewById(R.id.tv_mer_type);
            tvSupportBank= (TextView) findViewById(R.id.tv_support_bank);
            imgCheck= (ImageView) findViewById(R.id.img_check);
            lyItemChannel= (AutoLinearLayout) findViewById(R.id.ly_item_channel);
        }
    }

    //选中的item 回掉
    public interface OnItemCheckClickListener{
        void onCheck(int position,Channel channel);
    }

    public void setOnItemCheckClickListener(OnItemCheckClickListener onItemCheckClickListener){
        this.onItemCheckClickListener=onItemCheckClickListener;
    }
}
