package com.minmai.wallet.moudles.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.base.BaseRecyclerViewAdapter;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.BaseEntry;
import com.minmai.wallet.common.base.BaseObserver;
import com.minmai.wallet.common.uitl.MainUtil;
import com.minmai.wallet.common.uitl.RetrofitUtil;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.moudles.bean.response.ListLeaving;
import com.minmai.wallet.moudles.db.BankBackGround;
import com.minmai.wallet.moudles.dialog.CallUpDialog;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        Glide.with(context).load(mData.get(i).getUserHead()).into(viewHolder.imgHead);
        viewHolder.tvContent.setText(mData.get(i).getLeaveMessageContent());
        viewHolder.tvPhone.setText(mData.get(i).getPhone());
        if (mData.get(i).getReadState().equals("0")){
            viewHolder.tvIsRead.setText("未查看");
            Drawable drawableLeft = context.getResources().getDrawable(
                    R.mipmap.unlook_btn);
            viewHolder.tvIsRead.setCompoundDrawables(drawableLeft,null,null,null);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
        }else {
            viewHolder.tvIsRead.setText("已查看");
            Drawable drawableLeft = context.getResources().getDrawable(
                    R.mipmap.look_btn);
            drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
            viewHolder.tvIsRead.setCompoundDrawables(drawableLeft,null,null,null);
        }
        if (mData.get(i).getReadState().equals("0")){
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readMessage(viewHolder.tvIsRead,mData.get(i).getId());
                }
            });
        }
        viewHolder.tvPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CallUpDialog(getContext(), false, viewHolder.tvPhone.getText().toString().trim()).show();
            }
        });
    }

    //阅读留言
    public void readMessage(final TextView textView, String id){
        RetrofitUtil
                .getInstance()
                .initRetrofit().updateReadState(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<String>(context, MainUtil.loadTxt) {
                    @Override
                    protected void onSuccess(BaseEntry<String> t) throws Exception {
                        ToastUtils.show(t.getMsg());
                        Drawable drawableLeft = context.getResources().getDrawable(
                                R.mipmap.look_btn);
                        drawableLeft.setBounds(0, 0, drawableLeft.getMinimumWidth(), drawableLeft.getMinimumHeight());
                        textView.setCompoundDrawables(drawableLeft,null,null,null);
                        textView.setText("已查看");

                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        if (isNetWorkError) {
                            ToastUtils.show("网络连接失败，请检查网络");
                        }
                    }

                    @Override
                    protected void onError(BaseEntry<String> t) {
                        ToastUtils.show(t.getMsg());
                    }
                });
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
