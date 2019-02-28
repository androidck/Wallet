package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.hjq.toast.ToastUtils;
import com.minmai.wallet.R;
import com.minmai.wallet.moudles.adapter.ChannelAdapter;
import com.minmai.wallet.moudles.bean.response.Channel;

import java.util.List;

/**
 * 通道选择dialog
 */
public class PassagewayDialog extends Dialog implements View.OnClickListener{

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;
    List<Channel> list;
    private ChannelAdapter adapter;
    private Channel channels;
    private int positions;

    private OnSelectChannelListener onSelectChannelListener;

    private int isSelctChannel;//是否选中通道

    private String channelId;
    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public PassagewayDialog(Context context,List<Channel> list,String channelId, boolean isCancelable,OnSelectChannelListener onSelectChannelListener) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.list=list;
        this.onSelectChannelListener=onSelectChannelListener;
        this.channelId=channelId;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_passageway);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    //初始化页面
    private void initView() {
        //取消
        Button btnEsc=findViewById(R.id.btn_esc);
        //确定
        Button btnOk=findViewById(R.id.btn_ok);
        btnEsc.setOnClickListener(this);
        btnOk.setOnClickListener(this);
        RecyclerView recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter=new ChannelAdapter(context);
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemCheckClickListener(new ChannelAdapter.OnItemCheckClickListener() {
            @Override
            public void onCheck(int position, Channel channel) {
                isSelctChannel=1;
                positions=position;
                channels=channel;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_esc:
                dismiss();
                break;
            case R.id.btn_ok:
                if (isSelctChannel!=1){
                    ToastUtils.show("请选择快捷通道");
                }else {
                    onSelectChannelListener.onSelectChannel(positions,channels);
                    dismiss();
                }
                break;
        }
    }



    //通道选则回掉接口
    public interface OnSelectChannelListener{
        void onSelectChannel(int position,Channel channel);
    }


}
