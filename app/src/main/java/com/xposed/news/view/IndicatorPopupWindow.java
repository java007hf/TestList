package com.xposed.news.view;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by Jackie on 2015/12/30.
 */
public class IndicatorPopupWindow extends PopupWindow {
    private int mWidth, mHeight;

    public IndicatorPopupWindow(Context context, View contentView) {
        //计算宽和高
        calculateWidthAndHeight(context);
        setContentView(contentView);

        setWidth(mWidth);
        setHeight(mHeight);

        setFocusable(true);
        setTouchable(true);
        setOutsideTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());

        setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    dismiss();
                    return true;
                }
                return false;
            }
        });
    }

    private void calculateWidthAndHeight(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        mWidth = displayMetrics.widthPixels;
        mHeight = displayMetrics.heightPixels;
    }
}
