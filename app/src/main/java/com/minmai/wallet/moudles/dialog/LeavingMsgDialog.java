package com.minmai.wallet.moudles.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hjq.toast.ToastUtils;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.moudles.bean.request.LeaveMessageReq;
import com.minmai.wallet.moudles.bean.response.RefereeUserInfo;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.request.referee.RefereeContract;
import com.minmai.wallet.moudles.request.referee.RefereePresenter;

import java.util.List;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class LeavingMsgDialog extends Dialog implements RefereeContract.View {

    private boolean iscancelable;//控制点击dialog外部是否dismiss
    private View view;
    private Context context;

    private String realname;//推荐人姓名
    private String phone;//推荐人手机号
    String refereeUserId;//推荐人手机号
    String refereeHeadUrl;//推荐人手机号
    String refereeLevelName;//等级名称
    RefereePresenter presenter;
    DbUserInfoDao userInfoDao;

    //这里的view其实可以替换直接传layout过来的 因为各种原因没传(lan)
    public LeavingMsgDialog(Context context,  boolean isCancelable,String refereeName, String refereePhone,String refereeUserId,String refereeHeadUrl,String refereeLevelName) {
        super(context, R.style.ActionSheetDialogStyle);
        this.context = context;
        this.iscancelable = isCancelable;
        this.realname = refereeName;
        this.phone = refereePhone;
        this.refereeUserId=refereeUserId;
        this.refereeHeadUrl=refereeHeadUrl;
        this.refereeLevelName=refereeLevelName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_leaving_msg);//这行一定要写在前面
        setCancelable(iscancelable);//点击外部不可dismiss
        Window window = this.getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        initView();
    }

    private void initView() {
        presenter=new RefereePresenter(context,this);
        userInfoDao=MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        ImageView headImg=findViewById(R.id.img_head);
        TextView tvNickname=findViewById(R.id.tv_nickname);
        TextView tvUserNo=findViewById(R.id.tv_user_no);
        Button button=findViewById(R.id.btn_leaving_msg);
        tvNickname.setText(realname);
        tvUserNo.setText("ID："+phone);
        loadHeadImg(headImg);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeavingMsg();
                dismiss();
            }
        });
    }

    private void loadHeadImg(ImageView imageView){
        Glide.with(context).load(refereeHeadUrl).into(imageView);
    }

    @Override
    public void onSuccess(String msg) {
        if ("访问成功".equals(msg)){
            ToastUtils.show("留言成功");
        }
    }

    @Override
    public void fail(String msg) {
        ToastUtils.show(msg);
    }

    /**
     * 留言
     */
    public void LeavingMsg(){
        List<DbUserInfo> dbUserInfos=userInfoDao.loadAll();
        EditText editText=findViewById(R.id.ed_content);
        LeaveMessageReq leaveMessageReq=new LeaveMessageReq();
        leaveMessageReq.setRefereeId(refereeUserId);
        leaveMessageReq.setLeaveMessageContent(editText.getText().toString().trim());
        presenter.userLeaveMessage(dbUserInfos.get(0).getUserId(),leaveMessageReq);
    }
}
