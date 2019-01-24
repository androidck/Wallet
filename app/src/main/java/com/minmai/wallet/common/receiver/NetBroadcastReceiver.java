package com.minmai.wallet.common.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.minmai.wallet.common.base.MyActivity;
import com.minmai.wallet.common.uitl.NetUtil;


/**
 * 动态实时监听网络变化
 */
public class NetBroadcastReceiver extends BroadcastReceiver {

    //public NetChangeListener listener = MyActivity.listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 当网络发生变化，判断当前网络状态，并通过NetEvent回调当前网络状态
           /* if (listener != null) {
                listener.onChangeListener(netWorkState);
            }*/
        }
    }


    // 自定义接口
    public interface NetChangeListener {
        void onChangeListener(int status);
    }
}
