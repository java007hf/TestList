package com.victor.databindingdemo;

import android.content.Context;
import android.widget.Toast;

/** 任务类
 * Created by Victor on 2017/1/21.
 */
public class MyTask implements Runnable {

    private Context mContext;

    public MyTask(Context context) {
        mContext = context;
    }

    @Override
    public void run() {
        Toast.makeText(mContext, "执行任务", Toast.LENGTH_SHORT).show();
    }
}
