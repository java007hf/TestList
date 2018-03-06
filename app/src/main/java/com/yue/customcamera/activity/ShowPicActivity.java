package com.yue.customcamera.activity;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.yue.customcamera.AppConstant;
import ju.xposed.com.jumodle.R;

public class ShowPicActivity extends Activity {

    private ImageView img;
    private int picWidth;
    private int picHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_pic);

        picWidth = getIntent().getIntExtra(AppConstant.KEY.PIC_WIDTH, 0);
        picHeight = getIntent().getIntExtra(AppConstant.KEY.PIC_HEIGHT, 0);
        img = (ImageView)findViewById(R.id.img);
        img.setImageURI(Uri.parse(getIntent().getStringExtra(AppConstant.KEY.IMG_PATH)));
        img.setLayoutParams(new RelativeLayout.LayoutParams(picWidth, picHeight));
    }
}
