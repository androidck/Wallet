package com.minmai.wallet.moudles.fragment;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyApplication;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.constant.ActivityConstant;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.common.uitl.HideDataUtil;
import com.minmai.wallet.moudles.adapter.BottomDialogAdapter;
import com.minmai.wallet.moudles.bean.response.PerCenterInfo;
import com.minmai.wallet.moudles.bean.response.RefereeUserInfo;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.minmai.wallet.moudles.dialog.CallUpDialog;
import com.minmai.wallet.moudles.dialog.LeavingMsgDialog;
import com.minmai.wallet.moudles.dialog.LoginTipDialog;
import com.minmai.wallet.moudles.dialog.NickNameDialog;
import com.minmai.wallet.moudles.request.user.UserContract;
import com.minmai.wallet.moudles.request.user.UserPresenter;
import com.minmai.wallet.moudles.ui.me.MessageBoardActivity;
import com.minmai.wallet.moudles.ui.me.PersonalportraitActivity;
import com.minmai.wallet.moudles.ui.me.SetupActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 我的
 */
public class MyFragment extends MyLazyFragment implements UserContract.View {

    @BindView(R.id.img_head)
    CircleImageView imgHead;
    @BindView(R.id.tv_nickName)
    TextView tvNickName;
    @BindView(R.id.tv_user_no)
    TextView tvUserNo;
    @BindView(R.id.tv_integral)
    TextView tvIntegral;
    @BindView(R.id.ly_integral)
    AutoLinearLayout lyIntegral;
    @BindView(R.id.credit_num)
    TextView creditNum;
    @BindView(R.id.ly_credit_num)
    AutoLinearLayout lyCreditNum;
    @BindView(R.id.savings_num)
    TextView savingsNum;
    @BindView(R.id.ly_savings_num)
    AutoLinearLayout lySavingsNum;
    @BindView(R.id.tv_agency)
    TextView tvAgency;
    @BindView(R.id.ly_agency)
    AutoLinearLayout lyAgency;
    @BindView(R.id.video_recyclerView)
    RecyclerView videoRecyclerView;
    @BindView(R.id.tv_my_video)
    AutoLinearLayout tvMyVideo;
    @BindView(R.id.tv_message)
    AutoLinearLayout tvMessage;
    @BindView(R.id.tv_trade_record)
    AutoLinearLayout tvTradeRecord;
    @BindView(R.id.tv_share_download)
    AutoLinearLayout tvShareDownload;
    @BindView(R.id.my_customer)
    AutoLinearLayout myCustomer;
    @BindView(R.id.tv_online_customer)
    AutoLinearLayout tvOnlineCustomer;
    @BindView(R.id.tv_more_setup)
    AutoLinearLayout tvMoreSetup;
    @BindView(R.id.tv_help)
    AutoLinearLayout tvHelp;
    @BindView(R.id.ly_status)
    AutoLinearLayout lyStatus;

    List<String> list;
    @BindView(R.id.ly_salesman)
    AutoLinearLayout lySalesman;
    @BindView(R.id.ly_recommend)
    AutoLinearLayout lyRecommend;
    @BindView(R.id.img_ed_nickname)
    ImageView imgEdNickname;

    BottomDialogAdapter adapter;
    BottomDialog bottomDialog;
    @BindView(R.id.tv_phone_number)
    TextView tvPhoneNumber;
    @BindView(R.id.tv_refereePhone)
    TextView tvRefereePhone;
    @BindView(R.id.tv_referee_name)
    TextView tvRefereeName;

    private UserPresenter presenter;
    DbUserInfoDao userInfoDao;
    List<DbUserInfo> userInfos;

    String refereeName;//推荐人姓名
    String refereePhone;//推荐人手机号
    String refereeUserId;//推荐人手机号
    String refereeHeadUrl;//推荐人手机号
    String refereeLevelName;//等级名称


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_my;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        //获取状态栏高度
        Rect rectangle = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        LinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) lyStatus.getLayoutParams();
        lp.setMargins(0, rectangle.top, 0, 0);
        lyStatus.setLayoutParams(lp);
        adapter = new BottomDialogAdapter(getActivity());
    }

    @Override
    protected void initData() {
        presenter = new UserPresenter(getActivity(), this);
        userInfoDao = MyApplication.getInstances().getDaoSession().getDbUserInfoDao();
        list = new ArrayList<>();
        list.add("完善个人信息");
        list.add("发布招聘信息");
        list.add("查看招聘信息");
        adapter.setData(list);
        adapter.setOnItemClickListener(new BottomDialogAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                toast(list.get(position));
                bottomDialog.dismiss();
            }
        });
        getPerCenterInfo();
    }

    //获取个人中心资料
    public void getPerCenterInfo() {
        if (isLogin() == true) {
            userInfos = userInfoDao.loadAll();
            presenter.getUserPerCenterInfo(userInfos.get(0).getUserId());
            presenter.getRefereeUserInfo(userInfos.get(0).getUserId());
        }
    }

    public static MyFragment newInstance() {
        return new MyFragment();
    }


    @OnClick({R.id.img_head, R.id.ly_integral, R.id.ly_credit_num, R.id.ly_savings_num, R.id.ly_agency, R.id.tv_my_video, R.id.tv_message, R.id.tv_trade_record, R.id.tv_share_download, R.id.my_customer, R.id.tv_online_customer, R.id.tv_more_setup, R.id.tv_help, R.id.ly_salesman, R.id.ly_recommend, R.id.img_ed_nickname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_head://头像
                if (isLogin()==true){
                    startActivity(PersonalportraitActivity.class);
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_integral://积分
                if (isLogin()==true){

                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_credit_num://信用卡
                if (isLogin()==true){
                    ARouter.getInstance().build(ActivityConstant.CREDIT_CARD).navigation();
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_savings_num://储蓄卡
                if (isLogin()==true){
                    ARouter.getInstance().build(ActivityConstant.SAVING_CARD).navigation();
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_agency://代办
                if (isLogin()==true){

                }
                break;
            case R.id.tv_my_video://我的视频
                if (isLogin()==true){

                }else {
                    showLoginDialog();
                }
                break;
            case R.id.tv_message://留言板
                if (isLogin()==true){
                    startActivity(MessageBoardActivity.class);
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.tv_trade_record://交易记录
                if (isLogin()==true){
                    ARouter.getInstance().build(ActivityConstant.TRADE).navigation();
                }
                break;
            case R.id.tv_share_download://分享连接
                if (isLogin()==true){

                }else {
                    showLoginDialog();
                }
                break;
            case R.id.my_customer://我的客服
                if (isLogin()==true){
                    new CallUpDialog(getContext(), false, tvPhoneNumber.getText().toString().trim()).show();
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.tv_online_customer://在线客服
                if (isLogin()==true){
                }
                break;
            case R.id.tv_more_setup://更多设置
                if (isLogin()==true){
                    startActivity(SetupActivity.class);
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.tv_help://帮助
                if (isLogin()==true){
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_salesman://我是业务元
                if (isLogin()==true){
                    bottomDialog = new BottomDialog(getContext(), false, adapter);
                    bottomDialog.show();
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.ly_recommend://留言
                if (isLogin()==true){
                    new LeavingMsgDialog(getContext(),true, refereeName,refereePhone,refereeUserId,refereeHeadUrl,refereeLevelName).show();
                }else {
                    showLoginDialog();
                }
                break;
            case R.id.img_ed_nickname://编辑昵称
                if (isLogin()==true){
                    new NickNameDialog(getActivity(), true).show();
                }else {
                    showLoginDialog();
                }
                break;
        }
    }


    @Override
    public void onSetContent(Object object) {
        RefereeUserInfo refereeUserInfo= (RefereeUserInfo) object;
        refereeUserId=refereeUserInfo.getRefereeId();
        refereeHeadUrl=refereeUserInfo.getUserHead();
        refereeLevelName=refereeUserInfo.getLevelName();
        refereePhone=refereeUserInfo.getUserNo();

    }

    @Override
    public void onSetCodeId(String codeId) {

    }

    @Override
    public void onSuccess(String msg) {

    }

    @Override
    public void fail(String msg) {

    }

    @Override
    public void setPerCenterInfo(PerCenterInfo perCenterInfo) {
        tvIntegral.setText(perCenterInfo.getIntegralCalculus());//用户积分
        tvAgency.setText(perCenterInfo.getNeedTo());//待办
        creditNum.setText(perCenterInfo.getCreditCardCount());//信用卡数量
        savingsNum.setText(perCenterInfo.getDebitCardCount());//储蓄卡数量
        tvUserNo.setText("ID：" + perCenterInfo.getUserNo());//我的用户编号
        tvPhoneNumber.setText(perCenterInfo.getTelephone());//客服电话
        if ("1".equals(perCenterInfo.getRefereeExtendOne())) {
            tvRefereeName.setText(perCenterInfo.getRefereeUser());
            tvRefereePhone.setText(perCenterInfo.getRefereePhone());
        } else if ("2".equals(perCenterInfo.getRefereeExtendOne())) {
            tvRefereePhone.setText(HideDataUtil.hideCardNo(perCenterInfo.getRefereePhone()));
            tvRefereeName.setText(HideDataUtil.hideRealName(perCenterInfo.getRefereeUser()));
        }
        refereeName=tvRefereeName.getText().toString().trim();

    }


    //判断是否登录
    public boolean isLogin() {
        List<DbUserInfo> userInfos = userInfoDao.loadAll();
        if (userInfos == null || userInfos.size() == 0) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 显示
     */




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
