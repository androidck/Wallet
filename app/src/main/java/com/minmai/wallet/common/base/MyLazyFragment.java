package com.minmai.wallet.common.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hjq.toast.ToastUtils;
import com.minmai.wallet.common.greendao.DbUserInfoDao;
import com.minmai.wallet.moudles.db.DbUserInfo;
import com.minmai.wallet.moudles.dialog.BottomDialog;
import com.minmai.wallet.moudles.dialog.LoginTipDialog;
import com.minmai.wallet.moudles.ui.identity.IdentifyOneActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyThreeActivity;
import com.minmai.wallet.moudles.ui.identity.IdentifyTwoActivity;
import com.minmai.wallet.moudles.ui.main.MainActivity;
import com.minmai.wallet.moudles.web.BrowserActivity;
import com.minmai.wallet.moudles.web.SonicJavaScriptInterface;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 项目中 Fragment 懒加载基类
 */
public abstract class MyLazyFragment extends UILazyFragment {

    private Unbinder mButterKnife;// View注解


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        mButterKnife = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
    }

    @Override
    public void onPause() {
        super.onPause();
        // 友盟统计

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mButterKnife.unbind();
    }

    /**
     * 显示吐司
     */
    public void toast(CharSequence s) {
        ToastUtils.show(s);
    }

    public void toast(int id) {
        ToastUtils.show(id);
    }

    public void toast(Object object) {
        ToastUtils.show(object);
    }

    public void showLoginDialog(){
        new LoginTipDialog(getActivity(),false).show();
    }

    //跳转到浏览器
    public void startBrowserActivity(Context context, int mode, String url) {
        Intent intent = new Intent(context, BrowserActivity.class);
        intent.putExtra(BrowserActivity.PARAM_URL, url);
        intent.putExtra(BrowserActivity.PARAM_MODE, mode);
        intent.putExtra(SonicJavaScriptInterface.PARAM_CLICK_TIME, System.currentTimeMillis());
        startActivityForResult(intent, -1);
    }

    public boolean Authentication(int registerState){
        if (1==registerState){
            //实名认证第一步
            startActivity(IdentifyOneActivity.class);
            return true;
        }else if (2==registerState){
            //实名认字第二部
            startActivity(IdentifyTwoActivity.class);
            return true;
        }else if (3==registerState){
            //实名认证第三部
            startActivity(IdentifyThreeActivity.class);
            return true;
        }else {
            return false;
        }
    }



}