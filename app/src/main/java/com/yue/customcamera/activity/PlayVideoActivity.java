package com.yue.customcamera.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.VideoView;

import com.yue.customcamera.AppConstant;
import ju.xposed.com.jumodle.R;
import com.yue.customcamera.base.DefaultBaseActivity;

public class PlayVideoActivity extends DefaultBaseActivity {

    private VideoView videoView;
    private String video_path;

    @Override
    protected void initialize() {
        setContentView(R.layout.activity_play_video);
        video_path = getIntent().getStringExtra(AppConstant.KEY.VIDEO_PATH);
    }

    @Override
    protected void initView() {
        videoView = (VideoView)findViewById(R.id.videoView);
        ((TextView)findViewById(R.id.text_savepath)).setText("保存的路径为===" + video_path);
    }

    @Override
    protected void initData() {
        super.initData();
        videoView.setVideoPath(video_path);
        videoView.start();
    }
}
