package com.minmai.wallet.moudles.fragment;

import android.graphics.Rect;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minmai.wallet.R;
import com.minmai.wallet.common.base.MyLazyFragment;
import com.minmai.wallet.common.uitl.ViewUtil;
import com.minmai.wallet.moudles.adapter.MyPagerAdapter;
import com.minmai.wallet.moudles.fragment.extensionchild.ImageFragment;
import com.minmai.wallet.moudles.fragment.extensionchild.VideoFragment;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.minmai.wallet.common.uitl.DensityUtil.dip2px;

/**
 * 推广
 */
public class ExtensionFragment extends MyLazyFragment {
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPage)
    ViewPager viewPage;
    List<Fragment> fragmentList = new ArrayList<>();

    MyPagerAdapter adapter;
    @BindView(R.id.ly_tab)
    AutoLinearLayout lyTab;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_extension;
    }

    @Override
    protected int getTitleBarId() {
        return 0;
    }

    @Override
    protected void initView() {
        //获取状态栏高度
        Rect rectangle= new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        LinearLayout.LayoutParams lp = (AutoLinearLayout.LayoutParams) lyTab.getLayoutParams();
        lp.setMargins(0, rectangle.top, 0, 0);
        lyTab.setLayoutParams(lp);
        fragmentList.add(new VideoFragment());
        fragmentList.add(new ImageFragment());
        List<String> titleList = new ArrayList<>();
        titleList.add("视频");
        titleList.add("图片");
        ViewUtil.setTabLayoutIndicator(tabLayout);
        adapter = new MyPagerAdapter(getChildFragmentManager(), titleList, fragmentList);
        //设置下划线宽度

        viewPage.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPage);//此方法就是让tablayout和ViewPager联动
    }

    @Override
    protected void initData() {

    }

    public static ExtensionFragment newInstance() {
        return new ExtensionFragment();
    }


    public void reflex(final TabLayout tabLayout){
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout mTabStrip = (LinearLayout) tabLayout.getChildAt(0);

                    int dp10 = dip2px(tabLayout.getContext(), 10);
                    for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                        View tabView = mTabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView mTextView = (TextView) mTextViewField.get(tabView);

                        tabView.setPadding(0, 0, 0, 0);

                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int width = 0;
                        width = mTextView.getWidth();
                        if (width == 0) {
                            mTextView.measure(0, 0);
                            width = mTextView.getMeasuredWidth();
                        }
                        //设置tab左右间距为10dp  注意这里不能使用Padding 因为源码中线的宽度是根据 tabView的宽度来设置的
                        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        params.width = width ;
                        params.leftMargin = dp10;
                        params.rightMargin = dp10;
                        tabView.setLayoutParams(params);
                        tabView.invalidate();
                    }

                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

    }


}
