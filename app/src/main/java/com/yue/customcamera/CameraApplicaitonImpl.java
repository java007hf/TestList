package com.yue.customcamera;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.code.library.share.utils.DirectoryUtils;
import com.code.library.utils.DeviceInfoUtils;
import com.code.library.utils.LogUtils;

import org.ffmpeg.android.FfmpegController;

import java.io.File;
import java.io.IOException;

/**
 * Created by Wxcily on 15/10/30.
 */
public class CameraApplicaitonImpl {

    private static final String TAG = "CameraApplicaitonImpl";
    //Application单例
    private static CameraApplicaitonImpl instance;
    //Context
    private Context context;
    //屏幕尺寸
    private int screenWidth;
    private int screenHeight;
    //应用的最高申请内存
    private long maxMemory;
    //默认存储目录
    private String BASE_PATH;
    //APP默认路径
    private String APP_PATH;
    //缓存路径
    private String CACHE_PATH;
    //划图解析图片路径
    private String CACHE_PATH_FRAMES;
    //临时文件目录
    private String TEMP_PATH;
    private String VIDEO_TEMP_PATH;
    //图片保存目录
    private String PICTURE_PATH;
    private String FONT_CACHE_PATH;
    private FfmpegController fc;

    public String getVIDEO_PATH() {
        return VIDEO_PATH;
    }

    private String VIDEO_PATH;
    private String VIDEO_FRAMES_PATH;
    //UID
    private int uid = 0;
    //token
    private String token;
    //TitleBar高度
    private static int titleBarHeight = 0;
    private String GIF_PATH;
    private String APK_PATH;
    private String FILTER_PATH;
    private String SCENE_PATH;
    private String FONT_PATH;
    private int textHeight;
    private int textWidth;
    private boolean is_first = false;

    private CameraApplicaitonImpl() {}

    public static CameraApplicaitonImpl getInstance() {
        if (instance == null) {
            instance = new CameraApplicaitonImpl();
        }

        return  instance;
    }

    public void onCreate(Context context) {
        this.context = context;
//        CrashReport.initCrashReport(this, "900011702", AppConfig.DEBUG);//bugly
        screenWidth = DeviceInfoUtils.getScreenWidth(context);//获取屏幕宽度
        screenHeight = DeviceInfoUtils.getScreenHeight(context);//获取屏幕高度
        initDir(context);//初始化默认路径
        initMemorySize();//打印APP最大可以申请的内存
        initFFmpeg(context);
    }

    private void initFFmpeg(final Context context) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                File fileAppRoot = new File(
                        context.getApplicationInfo().dataDir);
                try {
                    fc = new FfmpegController(
                            context, fileAppRoot);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public String getFILTER_PATH() {
        return FILTER_PATH;
    }

    private void initDir(Context context) {
        //初始化应用存储路径
        BASE_PATH = DirectoryUtils.getDefaultStoragePath(context);
        APP_PATH = BASE_PATH + File.separator + AppConfig.APP_PATH_NAME;
        Log.d(TAG, "应用存储根目录:" + APP_PATH);
        CACHE_PATH = context.getExternalFilesDir(null) + File.separator + AppConfig.CACHE_PATH_NAME;
        CACHE_PATH_FRAMES = context.getExternalFilesDir(null) + File.separator + AppConfig.CACHE_PATH_FRAMES_NAME;
        Log.d(TAG, "缓存目录:" + CACHE_PATH);
        PICTURE_PATH = APP_PATH + File.separator + AppConfig.PICTURE_PATH_NAME;
        TEMP_PATH = APP_PATH + File.separator + AppConfig.TEMP_PATH_NAME;

        VIDEO_TEMP_PATH = context.getFilesDir() + File.separator + AppConfig.VIDEO_TEMP_PATH_NAME;
        VIDEO_PATH = APP_PATH + File.separator + AppConfig.VIDEO_PATH_NAME;
        VIDEO_FRAMES_PATH = context.getFilesDir() + File.separator + AppConfig.VIDEO_FRAMES_NAME;

        /*TEMP_PATH = APP_PATH + File.separator + AppConfig.TEMP_PATH_NAME;
        VIDEO_PATH = APP_PATH + File.separator + AppConfig.VIDEO_PATH_NAME;
        VIDEO_FRAMES_PATH = APP_PATH + File.separator + AppConfig.VIDEO_FRAMES_NAME;*/

        GIF_PATH = APP_PATH + File.separator + AppConfig.GIF_PATH_NAME;
        APK_PATH = APP_PATH + File.separator + AppConfig.APK_PATH_NAME;
        FILTER_PATH = context.getFilesDir() + File.separator + AppConfig.FILTER_PATH_NAME;
        SCENE_PATH = context.getFilesDir() + File.separator + AppConfig.SCENE_PATH_NAME;
        FONT_PATH = APP_PATH + File.separator + AppConfig.FONT_PATH_NAME;
        FONT_CACHE_PATH = APP_PATH + File.separator + AppConfig.FONT_CACHE_NAME;
        //判断是否存在,如不存在则创建目录
        File dir = null;
        dir = new File(VIDEO_PATH);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    //用于打印APP最多可申请的内存
    private void initMemorySize() {
        maxMemory = Runtime.getRuntime().maxMemory();
        Log.d(TAG, "最大申请内存:" + Long.toString(maxMemory / (1024 * 1024)) + "MB");
    }

    //根据后缀自动生成临时文件
    public String getTempFileNameForExtension(String extension) {
        return getTempPath() + File.separator + System.currentTimeMillis() + extension;
    }

    public String getTempFileName() {
        return getTempPath() + File.separator + System.currentTimeMillis();
    }

    //退出登录时需清除UID和TOKEN
    public void exit() {
        uid = 0;
        token = "";
    }

    public FfmpegController getFc() {
        return fc;
    }

    public void setFc(FfmpegController fc) {
        this.fc = fc;
    }

    public boolean is_first() {
        return is_first;
    }

    public void setIs_first(boolean is_first) {
        this.is_first = is_first;
    }

    public Context getContext() {
        return context;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public long getMaxMemory() {
        return maxMemory;
    }

    public String getBasePath() {
        return BASE_PATH;
    }

    public String getAppPath() {
        return APP_PATH;
    }

    public String getCachePath() {
        return CACHE_PATH;
    }

    public String getCACHE_PATH_FRAMES() {
        return CACHE_PATH_FRAMES;
    }

    public String getTempPath() {
        return TEMP_PATH;
    }

    public String getVIDEO_TEMP_PATH() {
        return VIDEO_TEMP_PATH;
    }

    public void setVIDEO_TEMP_PATH(String VIDEO_TEMP_PATH) {
        this.VIDEO_TEMP_PATH = VIDEO_TEMP_PATH;
    }

    public String getPicturePath() {
        return PICTURE_PATH;
    }

    public String getGifPath() {
        return GIF_PATH;
    }

    public String getApkPath() {
        return APK_PATH;
    }

    public int getUid() {
        return uid;
    }

    public int getTitleBarHeight() {
        return titleBarHeight;
    }

    public void setTitleBarHeight(int titleBarHeight) {
        this.titleBarHeight = titleBarHeight;
    }

    public String getSCENE_PATH() {
        return SCENE_PATH;
    }

    public void setTextHeight(int textHeight) {
        this.textHeight = textHeight;
    }

    public void setTextWidth(int textWidth) {
        this.textWidth = textWidth;
    }

    public int getTextWidth() {
        return textWidth;
    }
}
