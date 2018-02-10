package com.xposed.news.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by Jackie on 2016/1/8.
 * 提供禁止滑动的接口
 */
public class CustomViewPager extends ViewPager {

    private boolean mIsPagingEnabled = true;

    public CustomViewPager(Context context) {
        this(context, null);
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mIsPagingEnabled && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mIsPagingEnabled && super.onInterceptTouchEvent(event);
    }

    public void setIsPagingEnabled(boolean isPagingEnabled) {
        this.mIsPagingEnabled = isPagingEnabled;
    }
}
