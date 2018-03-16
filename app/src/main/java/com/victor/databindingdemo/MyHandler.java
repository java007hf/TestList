package com.victor.databindingdemo;

import android.view.View;
import android.widget.Toast;

/** 事件处理类
 * Created by Victor on 2017/1/21.
 */
public class MyHandler {
    private User user;

    public MyHandler(User user) {
        this.user = user;
    }

    public void showTost(View view) {
        user.phone = "1860000222"; //无效
        Toast.makeText(view.getContext(), "展示吐司", Toast.LENGTH_SHORT).show();
    }

    public void onEventListenerExecute(MyTask task) {
        task.run();
    }

}
