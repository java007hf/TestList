package com.code.library.utils;

import java.security.MessageDigest;

/**
 * Created by yue on 15/10/29.
 * MD5加密工具
 */
public class MD5Utils {
    private MD5Utils() {
    }

    /**
     * 将字符串转MD5字符串
     *
     * @param str
     * @return
     */
    public static String Md5(String str) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] md5Byte = md5.digest(str.getBytes("UTF8"));
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md5Byte.length; i++) {
                sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
            }
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节数组转为MD5字符串
     *
     * @param bytes
     * @return
     */
    public static String Md5(byte[] bytes) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            char[] HEX = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                    'a', 'b', 'c', 'd', 'e', 'f'};
            byte[] md5Byte = md5.digest(bytes);
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md5Byte.length; i++) {
                sb.append(HEX[(int) (md5Byte[i] & 0xff) / 16]);
                sb.append(HEX[(int) (md5Byte[i] & 0xff) % 16]);
            }
            return sb.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将字节数组转MD5字节数组
     *
     * @param bytes
     * @return
     */
    public static byte[] Md5Byte(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(bytes);
            return md.digest();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
