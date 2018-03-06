package com.yue.customcamera.activity;

import android.content.Intent;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.code.library.dialog.LoadingDialog;
import com.code.library.utils.BitmapUtils;
import com.code.library.utils.LogUtils;
import com.yue.customcamera.AppConfig;
import com.yue.customcamera.AppConstant;
import ju.xposed.com.jumodle.R;

import com.yue.customcamera.CameraApplicaitonImpl;
import com.yue.customcamera.base.DefaultBaseActivity;
import com.yue.customcamera.utils.CameraUtil;

import org.ffmpeg.android.FfmpegController;
import org.ffmpeg.android.ShellUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ShortVideoActivity extends DefaultBaseActivity implements SurfaceHolder.Callback, View.OnClickListener {
    private RelativeLayout bottomLayout;
    private int screenWidth;
    private int screenheight;
    private MediaRecorder mediaRecorder;
    private ImageView img_video_shutter;
    private ProgressBar progressBar;
    private int PROGRESS_MAX = 100;
    private Camera mCamera;
    private SurfaceView surfaceView;
    private SurfaceHolder mHolder;
    //默认前置或者后置相机 0:后置 1:前置
    private int mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
    //闪光灯模式 0:关闭 1: 开启 2: 自动
    private File file;
    private String pathName;
    private int video_width;
    private int video_height;
    private CamcorderProfile profile;
    private File file2;
    private LoadingDialog dialog;
    private FfmpegController fc;
    private int recorderRotation;

    @Override
    protected void onBefore() {
        super.onBefore();
        fc = CameraApplicaitonImpl.getInstance().getFc();
    }


    @Override
    protected void initialize() {
        setContentView(R.layout.activity_short_video);
        screenWidth = CameraApplicaitonImpl.getInstance().getScreenWidth();
        screenheight = CameraApplicaitonImpl.getInstance().getScreenHeight();

        dialog = new LoadingDialog.Builder(context).setCancelable(false).setCanceledOnTouchOutside(false)
                .setTitle(getString(R.string.generating)).setIcon(R.drawable.img_refresh_smallgrey).create();
    }

    @Override
    protected void initView() {
        bottomLayout = (RelativeLayout) findViewById(R.id.bottomLayout);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        mHolder = surfaceView.getHolder();
        mHolder.addCallback(this);
        img_video_shutter = (ImageView) findViewById(R.id.img_video_shutter);
        img_video_shutter.setOnClickListener(this);

        findViewById(R.id.img_camera_close).setOnClickListener(this);
        findViewById(R.id.img_camera_turn).setOnClickListener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setProgress(0);
        progressBar.setMax(PROGRESS_MAX);
    }

    protected void start() {
        try {
            pathName = System.currentTimeMillis() + "";
            //视频存储路径
            file = new File(CameraApplicaitonImpl.getInstance().getTempPath() + File.separator + pathName + AppConfig.MP4);

            //如果没有要创建
            BitmapUtils.makeDir(file);

            //初始化一个MediaRecorder
            if (mediaRecorder == null) {
                mediaRecorder = new MediaRecorder();
            } else {
                mediaRecorder.reset();
            }

            mCamera.unlock();
            mediaRecorder.setCamera(mCamera);
            //设置视频输出的方向 很多设备在播放的时候需要设个参数 这算是一个文件属性
            mediaRecorder.setOrientationHint(recorderRotation);

            //视频源类型
            mediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setAudioChannels(2);
            // 设置视频图像的录入源
            // 设置录入媒体的输出格式
//            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
            // 设置音频的编码格式
//            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            // 设置视频的编码格式
//            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);

            if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
            } /*else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_720P)) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_720P);
            } */ else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_1080P)) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_1080P);
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_HIGH)) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);
            } else if (CamcorderProfile.hasProfile(CamcorderProfile.QUALITY_LOW)) {
                profile = CamcorderProfile.get(CamcorderProfile.QUALITY_LOW);
            }

            if (profile != null) {
                profile.audioCodec = MediaRecorder.AudioEncoder.AAC;
                profile.audioChannels = 1;
                profile.audioSampleRate = 16000;

                profile.videoCodec = MediaRecorder.VideoEncoder.H264;
                mediaRecorder.setProfile(profile);
            }

            //视频尺寸
            mediaRecorder.setVideoSize(video_width, video_height);

            //数值越大 视频质量越高
            mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);

            // 设置视频的采样率，每秒帧数
//            mediaRecorder.setVideoFrameRate(5);

            // 设置录制视频文件的输出路径
            mediaRecorder.setOutputFile(file.getAbsolutePath());
            mediaRecorder.setMaxDuration(2000);

            // 设置捕获视频图像的预览界面
            mediaRecorder.setPreviewDisplay(surfaceView.getHolder().getSurface());

            mediaRecorder.setOnErrorListener(new MediaRecorder.OnErrorListener() {

                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    // 发生错误，停止录制
                    if (mediaRecorder != null) {
                        mediaRecorder.stop();
                        mediaRecorder.release();
                        mediaRecorder = null;
                    }
                }
            });

            mediaRecorder.setOnInfoListener(new MediaRecorder.OnInfoListener() {
                @Override
                public void onInfo(MediaRecorder mr, int what, int extra) {
                    //录制完成
                }
            });

            // 准备、开始
            mediaRecorder.prepare();
            mediaRecorder.start();

            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < PROGRESS_MAX; i++) {
                        try {
                            Thread.currentThread().sleep(20);

                            Message message = new Message();
                            message.what = 1;
                            message.obj = i;
                            handler.sendMessage(message);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            progressBar.setProgress((int) msg.obj);
            if ((int) msg.obj == PROGRESS_MAX - 1) {
                //录制完成
                img_video_shutter.setEnabled(true);

                if (mediaRecorder != null) {
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    releaseCamera();
                }
                compressThread(file.getName());
            }
        }
    };


    /**
     * 视频压缩
     *
     * @param FileName
     */
    private void compressThread(final String FileName) {
        if (dialog != null && !isFinishing()) {
            dialog.show();
        }
        new Thread() {
            @Override
            public void run() {
                compressSize();
            }

            /**
             * 视频压缩
             */
            private void compressSize() {
                // 准备压缩
                mHandler.sendEmptyMessage(R.string.state_compress);

                file2 = new File(CameraApplicaitonImpl.getInstance().getVIDEO_PATH(), FileName);

                if (!file2.exists()) {
                    try {
                        file2.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    fc.compress_clipVideo(file.getAbsolutePath(),
                            file2.getAbsolutePath(), mCameraId, video_width, video_height, 0, 0,
                            new ShellUtils.ShellCallback() {

                                @Override
                                public void shellOut(String shellLine) {
                                }

                                @Override
                                public void processComplete(int exitValue) {
                                    dialog.dismiss();
                                    if (exitValue != 0) {
                                        mHandler.sendEmptyMessage(R.string.state_compress_error);
                                    } else {
                                        mHandler.sendEmptyMessage(R.string.state_compress_end);
                                    }
                                }
                            });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }.start();
    }


    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case R.string.state_compress_end:
                    Intent intent = new Intent(activity, PlayVideoActivity.class);
                    intent.putExtra(AppConstant.KEY.VIDEO_PATH, file2.getPath());
                    startActivity(intent);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (mCamera == null) {
            mCamera = getCamera(mCameraId);
            if (mHolder != null && mCamera != null) {
                //开启预览
                startPreview(mCamera, mHolder);
            }
        }

        if (progressBar != null) {
            progressBar.setProgress(0);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    /**
     * 获取Camera实例
     *
     * @return
     */
    private Camera getCamera(int id) {
        Camera camera = null;
        try {
            camera = Camera.open(id);
        } catch (Exception e) {

        }
        return camera;
    }

    public void switchCamera() {
        releaseCamera();
        mCameraId = (mCameraId + 1) % mCamera.getNumberOfCameras();
        mCamera = getCamera(mCameraId);
        if (mHolder != null) {
            startPreview(mCamera, mHolder);
        }
    }

    /**
     * 预览相机
     */
    private void startPreview(Camera camera, SurfaceHolder holder) {
        try {
            setupCamera(camera);
            camera.setPreviewDisplay(holder);
            //获取相机预览角度， 后面录制视频需要用
            recorderRotation = CameraUtil.getInstance().getRecorderRotation(mCameraId);
            CameraUtil.getInstance().setCameraDisplayOrientation(this, mCameraId, camera);
            camera.startPreview();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置
     */
    private void setupCamera(Camera camera) {
        if (camera != null) {
            Camera.Parameters parameters = camera.getParameters();

            List<String> focusModes = parameters.getSupportedFocusModes();
            if (focusModes != null && focusModes.size() > 0) {
                if (focusModes.contains(
                        Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE)) {
                    //设置自动对焦
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                }
            }

            List<Camera.Size> videoSiezes = null;
            if (parameters != null) {
                //获取相机所有支持尺寸
                videoSiezes = parameters.getSupportedVideoSizes();
                for (Camera.Size size : videoSiezes) {
                }
            }

            if (videoSiezes != null && videoSiezes.size() > 0) {
                //拿到一个预览宽度最小为720像素的预览值
                Camera.Size videoSize = CameraUtil.getInstance().getPropVideoSize(videoSiezes, 720);
                video_width = videoSize.width;
                video_height = videoSize.height;
            }

            //这里第三个参数为最小尺寸 getPropPreviewSize方法会对从最小尺寸开始升序排列 取出所有支持尺寸的最小尺寸
            Camera.Size previewSize = CameraUtil.getInstance().getPropPreviewSize(parameters.getSupportedPreviewSizes(), video_width);
            parameters.setPreviewSize(previewSize.width, previewSize.height);

            Camera.Size pictrueSize = CameraUtil.getInstance().getPropPictureSize(parameters.getSupportedPictureSizes(), video_width);
            parameters.setPictureSize(pictrueSize.width, pictrueSize.height);

            camera.setParameters(parameters);

            /**
             * 设置surfaceView的尺寸 因为camera默认是横屏，所以取得支持尺寸也都是横屏的尺寸
             * 我们在startPreview方法里面把它矫正了过来，但是这里我们设置设置surfaceView的尺寸的时候要注意 previewSize.height<previewSize.width
             * previewSize.width才是surfaceView的高度
             * 一般相机都是屏幕的宽度 这里设置为屏幕宽度 高度自适应 你也可以设置自己想要的大小
             */
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(screenWidth, (screenWidth * video_width) / video_height);
            //这里当然可以设置拍照位置 比如居中 我这里就置顶了
            surfaceView.setLayoutParams(params);

            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(screenWidth, screenheight - screenWidth);
            layoutParams.addRule(RelativeLayout.BELOW, surfaceView.getId());
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            bottomLayout.setLayoutParams(layoutParams);
        }
    }

    /**
     * 释放相机资源
     */
    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(mCamera, mHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        mCamera.stopPreview();
        startPreview(mCamera, holder);
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        releaseCamera();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_video_shutter:
                start();
                img_video_shutter.setEnabled(false);
                break;

            case R.id.img_camera_close:
                finish();
                break;

            case R.id.img_camera_turn:
                switchCamera();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
