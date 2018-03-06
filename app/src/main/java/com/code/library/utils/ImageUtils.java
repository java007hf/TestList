package com.code.library.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;
import android.view.MotionEvent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by yue on 2015/11/27.
 */
public class ImageUtils {

    private static ImageUtils instance;

    public static ImageUtils getInstance(){
        if(instance == null){
            instance = new ImageUtils();
        }
        return instance;
    }

    /**
     * 图片编辑页图片调整时使用
     * @param bitmap
     * @param context
     * @return
     */
    public static Bitmap resizeBitmap(Bitmap bitmap, Context context, int width, int height) {
        int appWidth = bitmap.getWidth();
        int appHeight = bitmap.getHeight();
        bitmap = Bitmap.createScaledBitmap(bitmap, width, (height * appHeight) / appWidth, true);
        appWidth = bitmap.getWidth();
        appHeight = bitmap.getHeight();
        int cha = height - SystemUtils.dp2px(context, 125);

        if (bitmap.getHeight() > cha) {
            bitmap = Bitmap.createScaledBitmap(bitmap, cha * appWidth / appHeight, cha, true);
        }
        return bitmap;
    }


    /**
     * 下载贴纸
     * @param url
     * @return 返回进度
     */
    public static int downLoadUrl(String url, String filepath, String fileName) {
        File tempFile = new File(filepath);
        LogUtils.i("filepath===" + filepath);
        File[] files = null;

        if (!tempFile.exists()) {
            tempFile.mkdir();
        }

        URL myFileUrl = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        HttpURLConnection conn = null;

        byte[] Buffer = new byte[32768];// 32kb的缓存
        int size = 0;// 本次下载大小
        int nowsize = 0;// 当前下载大小
        int totalsize = 0;// 文件总大小
        int now = 0;
        try {
            myFileUrl = new URL(url);

            conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bufferedInputStream = new BufferedInputStream(
                    conn.getInputStream());// 获取buffer输入流
            totalsize = conn.getContentLength();
            File downloadFile = new File(filepath, fileName);

            fileOutputStream = new FileOutputStream(downloadFile);// 得到一个文件输出流
            // 保存文件
            while ((size = bufferedInputStream.read(Buffer)) != -1) {
                fileOutputStream.write(Buffer, 0, size);
            }
            files = tempFile.listFiles();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接的操作
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect();
        }
        return files.length;
    }

    //把一个url的网络图片变成一个本地的BitMap
    public static Bitmap returnUrlBitMap(String url) {
        URL myFileUrl = null;
        Bitmap bitmap = null;
        try {
            myFileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public interface DownloadProgress {
        void update(int progress);
    }

    private DownloadProgress downloadProgress;

    public void setDownloadProgress(DownloadProgress downloadProgress) {
        this.downloadProgress = downloadProgress;
    }

    /**
     * 下载推荐的app
     * @param url
     * @return
     */
    public void downLoadApk(String url, String filepath) {
        int now = 0;
//        String filepath = MyApplication.getInstance().getAppPath() + File.separator + dirName;
        File tempFile = new File(filepath);

        if (!tempFile.exists()) {
            tempFile.mkdir();
        }

        URL myFileUrl = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        HttpURLConnection conn = null;

        byte[] Buffer = new byte[32768];// 32kb的缓存
        int size = 0;// 本次下载大小
        int nowsize = 0;// 当前下载大小
        int totalsize = 0;// 文件总大小
        try {
            myFileUrl = new URL(url);

            conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bufferedInputStream = new BufferedInputStream(
                    conn.getInputStream());// 获取buffer输入流
            totalsize = conn.getContentLength();
            File downloadFile = new File(filepath);
            fileOutputStream = new FileOutputStream(downloadFile);// 得到一个文件输出流
            // 保存文件
            while ((size = bufferedInputStream.read(Buffer)) != -1) {
                fileOutputStream.write(Buffer, 0, size);
                nowsize = nowsize + size;
                float f = (float) nowsize / totalsize;
                now = (int) (f * 100);
                downloadProgress.update(now);
            }
            is.close();
        } catch (Exception e) {
            Log.d("downLoadException", "downLoadException");
        } finally {
            // 关闭连接的操作
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect();
        }
    }

    /**
     * 下载推荐的所有app
     * @param url
     * @return
     */
    public void downLoadAllApk(String url, String filepath) {
        int now = 0;
//        String filepath = MyApplication.getInstance().getAppPath() + File.separator + dirName;
        File tempFile = new File(filepath);

        if (!tempFile.exists()) {
            tempFile.mkdir();
        }

        URL myFileUrl = null;
        BufferedInputStream bufferedInputStream = null;
        FileOutputStream fileOutputStream = null;
        HttpURLConnection conn = null;

        byte[] Buffer = new byte[32768];// 32kb的缓存
        int size = 0;// 本次下载大小
        int nowsize = 0;// 当前下载大小
        int totalsize = 0;// 文件总大小
        try {
            myFileUrl = new URL(url);

            conn = (HttpURLConnection) myFileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bufferedInputStream = new BufferedInputStream(
                    conn.getInputStream());// 获取buffer输入流
            totalsize = conn.getContentLength();
            File downloadFile = new File(filepath);
            fileOutputStream = new FileOutputStream(downloadFile);// 得到一个文件输出流
            // 保存文件
            while ((size = bufferedInputStream.read(Buffer)) != -1) {
                fileOutputStream.write(Buffer, 0, size);
            }
            is.close();
        } catch (Exception e) {
            Log.d("downLoadException", "downLoadException");
        } finally {
            // 关闭连接的操作
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bufferedInputStream != null) {
                try {
                    bufferedInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            conn.disconnect();
        }
    }

    //提取图像Alpha位图
    public static Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
        //BitmapDrawable的getIntrinsicWidth（）方法，Bitmap的getWidth（）方法
        //注意这两个方法的区别
        //Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmapDrawable.getIntrinsicWidth(), mBitmapDrawable.getIntrinsicHeight(), Config.ARGB_8888);
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        Canvas mCanvas = new Canvas(mAlphaBitmap);
        Paint mPaint = new Paint();

        mPaint.setColor(mColor);
        //从原位图中提取只包含alpha的位图
        Bitmap alphaBitmap = mBitmap.extractAlpha();
        //在画布上（mAlphaBitmap）绘制alpha位图
        mCanvas.drawBitmap(alphaBitmap, 0, 0, mPaint);

        return mAlphaBitmap;
    }


    public static float rotationforTwo(MotionEvent event) {
        double delta_x = (event.getX(0) - event.getX(1));
        double delta_y = (event.getY(0) - event.getY(1));
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }

    // 得到两个点的距离
    public static float spacing(PointF p1, PointF p2) {
        float x = p1.x - p2.x;
        float y = p1.y - p2.y;
        return (float) Math.sqrt(x * x + y * y);
    }

    // 得到两个点的中点
    public static PointF midPoint(PointF p1, PointF p2) {
        PointF point = new PointF();
        float x = p1.x + p2.x;
        float y = p1.y + p2.y;
        point.set(x / 2, y / 2);
        return point;
    }

    // 旋转
    public static float rotation(PointF p1, PointF p2) {
        double delta_x = (p1.x - p2.x);
        double delta_y = (p1.y - p2.y);
        double radians = Math.atan2(delta_y, delta_x);
        return (float) Math.toDegrees(radians);
    }
}
