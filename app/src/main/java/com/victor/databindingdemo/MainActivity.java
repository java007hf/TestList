package com.victor.databindingdemo;

import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.databinding.ObservableInt;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import ju.xposed.com.jumodle.R;
import ju.xposed.com.jumodle.databinding.ActivityMvvmMainBinding;

public class MainActivity extends AppCompatActivity {
    private ObservableInt visibility = new ObservableInt();
    private ActivityMvvmMainBinding binding;
    private User user = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 要用setContentView(Activity activity, int layoutId)代替该方法
        //setContentView(R.layout.activity_main);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_main, new MyComponent());
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.setVisibility(visibility);

        fill();
    }

    private void fill() {
        // 初始化数据
        user.firstName = "wang";
        user.lastName = "yingli";
        user.phone = "1860000000";
        user.isShowPhone = true;
        // 绑定数据
        binding.setUser(user);

        // 绑定方法和监听
        MyHandler handler = new MyHandler(user);
        MyTask task = new MyTask(this);
        binding.setHandler(handler);
        binding.setTask(task);

        ModelAdapter adapter = new ModelAdapter(user);
        binding.setAdapter(adapter);

        //ObservableField
        visibility.set(user != null ? View.VISIBLE : View.GONE);
        adapter.setFirstName("aaa");
    }

    @Override
    protected void onResume() {
        user.phone = "1860000333"; //有效，因为setUser调用了handler message放入到队列不会立即执行
        super.onResume();
    }

}
