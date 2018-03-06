package com.code.library.share.utils;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by yue on 15/10/29.
 * 用于生成目录的工具
 */
public class DirectoryUtils {
    private DirectoryUtils() {
    }

    /**
     * 返回默认存储路径
     *
     * @return
     */
    public static String getDefaultStoragePath(Context context) {
        //如果存在外部存储设备则直接返回
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        //如果未挂载外部存储设备,则遍历内部存储卡
        String path = null;
        ArrayList<String> devMountList = getDevMountList();

        for (String devMount : devMountList) {
            File file = new File(devMount);
            if (file.isDirectory() && file.canWrite()) {
                path = file.getAbsolutePath();
                long timeStamp = System.currentTimeMillis();
                File testWritable = new File(path, "test_" + timeStamp);
                if (testWritable.mkdirs()) {
                    testWritable.delete();
                    break;
                } else {
                    path = null;
                }
            }
        }

        if (path != null) {
            return new File(path).getAbsolutePath();
        }

        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 遍历 "system/etc/vold.fstab” 文件，获取全部的Android的挂载点信息
     *
     * @return
     */
    private static ArrayList<String> getDevMountList() {
        String[] toSearch = readFile("/etc/vold.fstab").split(" ");
        ArrayList<String> out = new ArrayList<String>();
        for (int i = 0; i < toSearch.length; i++) {
            if (toSearch[i].contains("dev_mount")) {
                if (new File(toSearch[i + 2]).exists()) {
                    out.add(toSearch[i + 2]);
                }
            }
        }
        return out;
    }

    public static String readFile(String filePath) {
        String fileContent = "";
        File file = new File(filePath);
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(new FileInputStream(file));
            reader = new BufferedReader(is);
            String line = null;
            int i = 0;
            while ((line = reader.readLine()) != null) {
                fileContent += line + " ";
            }
            reader.close();
            return fileContent;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileContent;
    }

}
