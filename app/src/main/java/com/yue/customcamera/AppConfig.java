package com.yue.customcamera;

import android.graphics.Bitmap.Config;

import java.io.File;


/**
 * Created by Wxcily on 15/10/31.
 * 项目全局配置文件
 */
public class AppConfig {
    /**
     * 调试部分
     */
    //DEBUG模式开关
    public static final boolean DEBUG = true;
    //DEBUG TAG
    public static final String TAG = "CustomCamera";

    /**
     * OkHttp
     */
    //连接超时的时间
    public static final int ConnectTimeout = 5;
    //写入超时的时间
    public static final int WriteTimeout = 10;
    //读取超时的时间
    public static final int ReadTimeout = 10;
    //验证码重发时间
    public static final int ResendTime = 60;
    //okHttp Tag
    public static final String OkHttpTag = "default";
    /**
     * 缓存路径
     */
    //应用默认文件夹名
    public static final String APP_PATH_NAME = "CustomCamera";
    //应用默认缓存文件夹名
    public static final String CACHE_PATH_NAME = "cache";
    public static final String CACHE_PATH_FRAMES_NAME = "cache_frames";
    //应用默认临时文件文件夹名
    public static final String TEMP_PATH_NAME = "temp";
    //推荐 APK 文件夹
    public static final String APK_PATH_NAME = "apk";
    //应用默认图片保存文件夹名
    public static final String PICTURE_PATH_NAME = "pictrue";

    public static final String VIDEO_PATH_NAME = "VIDEO";

    public static final String VIDEO_FRAMES_NAME = "FRAMES";
    //GIF缓存文件夹名
    public static final String GIF_PATH_NAME = "Funny";
    //初始化滤镜文件夹
    public static final String FILTER_PATH_NAME = "filter";
    //情景目录
    public static final String SCENE_PATH_NAME = "scene";
    //Fresoc缓存文件夹
    public static final String FresocCache = "fresoc";
    //ImageLoader缓存文件夹
    public static final String ImageLoaderCache = "imageloader";
    //保存的图片格式
    public static final String pic_format_png = ".png";
    public static final String pic_format_gif = ".gif";
    public static final String pic_format_jpeg = ".jpeg";
    public static final String fileApkSuffix = ".apk";
    //ImageLoader本地缓存大小
    public static final int ImageLoaderCacheSzie = 100 * 1024 * 1024;
    //Fresoc本地缓存大小
    public static final int FresocCacheSzie = 100 * 1024 * 1024;
    /**
     * 图片压缩设置
     */
    //压缩图片的默认质量
    public static final int ImageQuality = 90;
    //图片色彩设置
    public static final Config BitmapConfig = Config.ARGB_8888;
    public static final String MP4 = ".mp4";

    //话题详情热门显示9张
    public static final int TOPIC_DETAI_TOP_LIMIT = 9;

    //话题详情实时刷新一次20张
    public static final int TOPIC_DETAIL_NOW_LIMIT = 15;
    //所有话题刷新
    public static final int ALL_TOPIC_LIMIT = 20;
    public static final int FILTER_COUNT = 8;
    public static final String GUIDEPIC = "GUIDEPIC";
    public static final String VERSION_KEY = "VERSION_KEY";
    //默认图片压缩尺寸
    /*
    上传类型
     */
    public static final String IMAGE = "image";
    public static final String VIDEO = "video";
    public static final String AVATAR = "avatar";


    //友盟计数
    public static final String SHAREACTION = "ShareAction";

    /**
     * 其他参数
     */
    //注册时默认的性别
    //选择图片页面弹出对话框全部图片项图标
    //评分解锁弹出次数 3为计数4次
    public static final int HOME_COMMENT_COUNT = 3;
    //本地存储蒙版数量
    public static final int SCENE_COUNT = 3;
    //选择照片Gridview每行显示数
    public static final int SelectPhotoGridViewNum = 4;
    //频道页默认的翻页时间
    public static final int DefaultPlayTime = 5; //0 , 3 , 5 , 8
    //pager提前预加载数据
    public static final int PagerPreLoading = 5;
    //pager每次的加载量
    public static final int PagerLoadNum = 20;
    //评论每次的加载量
    public static final int CommentLoadNum = 20;
    //黑名单列表每次加载量
    public static final int BlackListLoadNum = 20;
    //关注列表每次加载量
    public static final int FollowListLoadNum = 20;
    //粉丝列表每次加载量
    public static final int FansListLoadNum = 20;
    //消息中的几个分类的加载量
    public static final int MessageListLoadNum = 20;
    //频道实时List的加载量
    public static final int ChannelListLoadNum = 40;
    //瀑布List的提前加载量
    public static final int ChannelListAdvance = 10;
    //列表提前加载量
    public static final int DefaultListAdvance = 5;
    //pager图片缓存数量
    public static final int PagerCachePage = 5;
    //双击的延迟时间
    public static final int DoubleClickTime = 300;
    //举报最多字数
    public static final int ReportMaxLength = 100;
    //图片Gridveiw每次的加载量
    public static final int GridImageLoadNum = 30;
    //个人页图片提前加载量
    public static final int GridImageLoadAdvance = 15;
    //图片详情评论提前加载量
    public static final int CommentLoadAdvance = 3;
    //Feed流的加载数量
    public static final int FeedLoadNum = 30;
    //Feed流提前加载量
    public static final int FeedAdvance = 5;
    //回复评论的最大字数
    public static final int CommentReportMaxLength = 140;
    //昵称最大显示长度
    public static final int NickNameShowLength = 15;
    //签名的最大长度
    public static final int SignLength = 200;
    //每日最大上传图片数量
    public static final int UploadImageNumber = 20;
    public static int BlackWhiteFilterPosition;
    //当前的Filter
    public static String FONT_PATH_NAME = "FONT_PATH" + File.separator;
    public static String FONT_CACHE_NAME = "FONT_CACHE" + File.separator;
    public static String CUTIE_PACKAGENAME = "com.funny.cutie";
    public static String IS_NOWIFI_LOADING = "IS_NOWIFI_LOADING";
    public static String VIDEO_TEMP_PATH_NAME = "VIDEO_TEMP_PATH_NAME";
}