package com.xposed.news.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import ju.xposed.com.jumodle.R;

/**
 * Created by Jackie on 2016/1/8.
 * 侧滑菜单
 */
public class SlidingMenu extends HorizontalScrollView {
    private static final String TAG = "SlidingMenu";
    private LinearLayout mWrapperLayout;
    private ViewGroup mMenuView;
    private ViewGroup mMainView;

    private int mScreenWidth;
    private int mMenuWidth;
    private int mMenuPaddingRight;

    private boolean mIsOnce = false;

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScreenWidth = context.getResources().getDisplayMetrics().widthPixels;
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        mMenuPaddingRight = ta.getDimensionPixelSize(R.styleable.SlidingMenu_paddingRight, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, 50, context.getResources().getDisplayMetrics()));
        Log.d(TAG, "SlidingMenu mMenuPaddingRight = " + mMenuPaddingRight);
        ta.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!mIsOnce) {
            mWrapperLayout = (LinearLayout) getChildAt(0);
            mMenuView = (ViewGroup) mWrapperLayout.getChildAt(0);
            mMainView = (ViewGroup) mWrapperLayout.getChildAt(1);
            mMenuView.getLayoutParams().width = mMenuWidth = mScreenWidth - mMenuPaddingRight;
            mMainView.getLayoutParams().width = mScreenWidth;
            mIsOnce = true;
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                if (getScrollX() > mMenuWidth / 2) {
                    scrollTo(mMenuWidth, 0);
                } else {
                    scrollTo(0, 0);
                }
                return true;
        }
        return super.onTouchEvent(ev);
    }
}
