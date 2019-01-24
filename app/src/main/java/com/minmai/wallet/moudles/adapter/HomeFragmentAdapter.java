package com.minmai.wallet.moudles.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.hjq.base.BaseFragmentAdapter;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.moudles.fragment.ExtensionFragment;
import com.minmai.wallet.moudles.fragment.FindFragment;
import com.minmai.wallet.moudles.fragment.HomeFragment;
import com.minmai.wallet.moudles.fragment.MyFragment;

import java.util.List;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2018/10/18
 *    desc   : 主页界面 ViewPager + Fragment 适配器
 */
public final class HomeFragmentAdapter extends BaseFragmentAdapter<MyLazyFragment> {

    public HomeFragmentAdapter(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void init(FragmentManager manager, List<MyLazyFragment> list) {
        list.add(HomeFragment.newInstance());
        list.add(FindFragment.newInstance());
        list.add(ExtensionFragment.newInstance());
        list.add(MyFragment.newInstance());
    }
}