package com.code.library.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Created by yue on 2015/12/7.
 */
public class FileUtil {

    private static FileUtil fileUtil;
    private boolean flag;

    public static FileUtil getFileUtil() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    public FileUtil(){}


    public String readFileData(String fileName, Context context) {

        int res = 0;
        StringBuffer fileContent = new StringBuffer("");
        try {
            FileInputStream fin = context.openFileInput(fileName);
            int length = fin.available();
            byte[] buffer = new byte[length];
            fin.read(buffer);
            while ((res = fin.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, res));
            }
            fin.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileContent.toString();

    }

    /**
     * 读取文件 采用StringBuilder
     *
     * @param strFilePath
     * @return
     */
    public String readTxtFile(String strFilePath) {
        String path = strFilePath;
        StringBuilder builder = new StringBuilder();
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.d("TestFile", "The File doesn't not exist.");
        } else {
            try {
                InputStream instream = new FileInputStream(file);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while ((line = buffreader.readLine()) != null) {
                        builder.append(line + "\n");
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.d("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.d("TestFile", e.getMessage());
            }
        }
        return builder.toString();
    }

    /**
     * 删除目录（文件夹）以及目录下的文件
     *
     * @param sPath 被删除目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public boolean deleteDirectory(String sPath) {
        //如果sPath不以文件分隔符结尾，自动添加文件分隔符
        if (!sPath.endsWith(File.separator)) {
            sPath = sPath + File.separator;
        }
        File dirFile = new File(sPath);
        //如果dir对应的文件不存在，或者不是一个目录，则退出
        if (!dirFile.exists() || !dirFile.isDirectory()) {
            return false;
        }
        flag = true;
        //删除文件夹下的所有文件(包括子目录)
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            //删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag) break;
            } //删除子目录
            else {
                flag = deleteDirectory(files[i].getAbsolutePath());
                if (!flag) break;
            }
        }
        if (!flag) return false;
        //删除当前目录
        if (dirFile.delete()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 删除单个文件
     *
     * @param sPath 被删除文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public boolean deleteFile(String sPath) {
        flag = false;
        File file = new File(sPath);
        // 路径为文件且不为空则进行删除
        if (file.isFile() && file.exists()) {
            file.delete();
            flag = true;
        }
        return flag;
    }


    /**
     * 读取sd卡上指定后缀的所有文件
     *
     * @param files    返回的所有文件
     * @param filePath 路径(可传入sd卡路径)
     * @param suffere  后缀名称 比如 .gif
     * @return
     */
    public List<String> getSuffixFile(List<String> files, String filePath, String suffere) {

        File f = new File(filePath);

        if (!f.exists()) {
            return null;
        }

        String path = null;
        File[] subFiles = f.listFiles();

        if (subFiles == null) {
            if (path != null) {
                getSuffixFile(files, path, suffere);
            } else {
                return null;
            }
        }

        for (int i = 0; i < subFiles.length; i++) {
            if (subFiles[i].isFile() && subFiles[i].getName().endsWith(suffere)) {
                files.add(subFiles[i].getAbsolutePath());
            } else if (subFiles[i].isDirectory()) {
                getSuffixFile(files, subFiles[i].getAbsolutePath(), suffere);

                if (i + 1 < subFiles.length) {
                    path = subFiles[i + 1].getAbsolutePath();
                }

            } else {
                //非指定目录文件 不做处理
            }

        }

        return files;
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }


}
