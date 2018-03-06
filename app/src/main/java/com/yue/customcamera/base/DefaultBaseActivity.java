package com.yue.customcamera.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.code.library.utils.ActivityManager;
import com.yue.customcamera.CameraApplicaitonImpl;
import ju.xposed.com.jumodle.R;

/**
 * Created by Wxcily on 16/1/5.
 */
public abstract class DefaultBaseActivity extends BaseActivity {

    protected Context context;
    protected Activity activity;

    protected ImageButton titleBack;
    protected TextView titleText, titleAction;
    protected ImageButton titleActionImg;
    protected int screenWidth;
    protected int screenHeight;

    protected boolean addTask = true;

    protected void thisHome() {
        this.addTask = false;
    }

    @Override
    protected void onBefore() {
        super.onBefore();
        this.context = this;
        this.activity = this;
        screenWidth = CameraApplicaitonImpl.getInstance().getScreenWidth();
        screenHeight = CameraApplicaitonImpl.getInstance().getScreenHeight();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (addTask)
            ActivityManager.getInstance().addActivity(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (addTask)
            ActivityManager.getInstance().delActivity(this);
    }

    /**
     * 增加默认的界面切换动画
     */
    @Override
    public void startActivity(Intent intent) {
        startActivity(intent, true);
    }

    public void startActivity(Intent intent, boolean anim) {
        super.startActivity(intent);
        if (anim) overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode, true);
    }

    public void startActivityForResult(Intent intent, int requestCode, boolean anim) {
        super.startActivityForResult(intent, requestCode);
        if (anim) overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }

    @Override
    public void finish() {
        finish(true);
    }

    public void finish(boolean anim) {
        super.finish();
        if (anim) overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
    }

    protected void getTitleBar() {
        titleBack = (ImageButton) findViewById(R.id.title_back);
        titleText = (TextView) findViewById(R.id.title_text);
        titleAction = (TextView) findViewById(R.id.title_action);
    }

    /*protected void getImgTitleBar() {
        titleBack = (ImageButton) findViewById(R.id.title_back);
        titleText = (TextView) findViewById(R.id.title_text);
        titleActionImg = (ImageButton) findViewById(R.id.title_action);
    }*/

}
