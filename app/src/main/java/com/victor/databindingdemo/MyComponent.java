package com.victor.databindingdemo;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.TextView;

/**
 * Created by benylwang on 2018/3/14.
 */

public class MyComponent implements android.databinding.DataBindingComponent {
    @BindingAdapter("android:text")
    public static void setText(TextView view, String text) {
        view.setText(text);
        view.setTextColor(0xffff0000);
    }

    @BindingAdapter("android:alpha")
    public void setAlpha(View view, float alpha) {
        view.setAlpha(0.5f);
    }

    @Override
    public MyComponent getMyComponent() {
        return this;//new MyComponent();
    }
}
