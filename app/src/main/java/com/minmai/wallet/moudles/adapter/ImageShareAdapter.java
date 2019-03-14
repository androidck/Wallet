package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.hjq.base.BaseRecyclerViewAdapter;
import com.itheima.roundedimageview.RoundedImageView;
import com.minmai.wallet.R;
import com.minmai.wallet.moudles.bean.request.AppSpread;
import com.minmai.wallet.moudles.bean.response.Channel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 推广页面适配器
 */
public class ImageShareAdapter extends BaseRecyclerViewAdapter<ImageShareAdapter.ViewHolder>{

    private List<AppSpread> mData;
    private Context mContext;
    private OnItemClickListener onItemClickListener;
    private int mSelectedPos = 0;   //实现单选，保存当前选中的position
    private OnItemOneListener onItemOneListener;
    public ImageShareAdapter(Context context) {
        super(context);
        this.mContext=context;
        mData=new ArrayList<>();//防止为设置数据源时为空
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(viewGroup,R.layout.item_spread_img);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int position) {
        Glide.with(mContext).load(mData.get(position).getSmallPicture()).into(viewHolder.roundedImageView);
        viewHolder.isClick.setChecked(mData.get(position).isSelect());
        if (mData.get(position).isSelect()==true&&position==0){
            onItemOneListener.onOneClick(mData.get(0).getBigPicture());
        }
        viewHolder.isClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(mSelectedPos).setSelect(false);
                mSelectedPos = position;
                mData.get(mSelectedPos).setSelect(true);
                notifyDataSetChanged();
                onItemClickListener.onItemShareClick(mSelectedPos,mData.get(position).getBigPicture());
            }
        });
    }




    @Override
    public int getItemCount() {
        return mData == null ? 0: mData.size();
    }

    public void setData(List<AppSpread> list){
        this.mData=list;
        for (int i=0;i<mData.size();i++){
            if (mData.get(i).isSelect()){
                mSelectedPos=i;
            }
        }
    }

    public List<AppSpread> getData(){
        return mData;
    }

    class ViewHolder extends BaseRecyclerViewAdapter.ViewHolder{
        ImageView roundedImageView;
        CheckBox isClick;
        RelativeLayout lyItemAdvert;
        public ViewHolder(ViewGroup parent, int layoutId) {
            super(parent, layoutId);
            roundedImageView= (ImageView) findViewById(R.id.iv_promotion_img);
            lyItemAdvert= (RelativeLayout) findViewById(R.id.ly_item_advert);
            isClick= (CheckBox) findViewById(R.id.cb_selected_promotion);
        }
    }

    public interface OnItemClickListener{
        void onItemShareClick(int position,String bigImg);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemOneListener{
        void onOneClick(String bigImg);
    }

    public void setOnItemOneListener(OnItemOneListener onItemOneListener) {
        this.onItemOneListener = onItemOneListener;
    }
}
