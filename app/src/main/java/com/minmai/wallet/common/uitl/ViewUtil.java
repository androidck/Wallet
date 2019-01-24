package com.minmai.wallet.common.uitl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.minmai.wallet.R;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Field;

/**
 * 布局移除工具类
 */
public class ViewUtil {

    /**
     * 从父 view 中移除自己
     * @param child
     */
    public static void removeSelfFromParent(View child){
        if (child != null){
            ViewGroup parent = (ViewGroup)child.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                parent.removeView(child);
            }
            parent.removeView(child);
        }
    }

    //设置圆角圆角北京
    private Drawable setDrawable(String colorStr) {
        RoundRectShape rr = new RoundRectShape(new float[]{15, 15, 15, 15, 15, 15, 15, 15},
                null, null); //60px = 20dp
        ShapeDrawable drawable = new ShapeDrawable(rr);
        drawable.getPaint().setColor(Color.parseColor(colorStr)); //指定填充颜色
        drawable.getPaint().setStyle(Paint.Style.FILL); // 指定填充模式
        return drawable;
    }

    /**
     * 设置tabLayout 下划线宽度
     * @param tabLayout
     */
    public static void setTabLayoutIndicator(final TabLayout tabLayout) {
        //了解源码得知 线的宽度是根据 tabView的宽度来设置的
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                try {
                    Field field = tabLayout.getClass().getDeclaredField("mTabStrip");
                    field.setAccessible(true);
                    //拿到tabLayout的mTabStrip属性
                    LinearLayout tabStrip = (LinearLayout) field.get(tabLayout);
                    for (int i = 0, count = tabStrip.getChildCount(); i < count; i++) {
                        View tabView = tabStrip.getChildAt(i);
                        //拿到tabView的mTextView属性  tab的字数不固定一定用反射取mTextView
                        Field mTextViewField = tabView.getClass().getDeclaredField("mTextView");
                        mTextViewField.setAccessible(true);
                        TextView textView = (TextView) mTextViewField.get(tabView);
                        tabView.setPadding(0, 0, 0, 0);
                        //因为我想要的效果是   字多宽线就多宽，所以测量mTextView的宽度
                        int textWidth = 0;
                        textWidth = textView.getWidth();
                        if (textWidth == 0) {
                            textView.measure(0, 0);
                            textWidth = textView.getMeasuredWidth();
                        }
                        int tabWidth = 0;
                        tabWidth = tabView.getWidth();
                        if (tabWidth == 0) {
                            tabView.measure(0, 0);
                            tabWidth = tabView.getMeasuredWidth();
                        }
                        LinearLayout.LayoutParams tabViewParams = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                        int margin = (tabWidth - textWidth) / 2;
                        //LogUtils.d("textWidth=" + textWidth + ", tabWidth=" + tabWidth + ", margin=" + margin);
                        tabViewParams.leftMargin = margin;
                        tabViewParams.rightMargin = margin;
                        tabView.setLayoutParams(tabViewParams);
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
