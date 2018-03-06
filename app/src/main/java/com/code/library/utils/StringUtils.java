package com.code.library.utils;

import android.text.TextPaint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yue on 15/10/29.
 * 字符串处理
 */
public class StringUtils {
	private StringUtils() {
	}

	private static final double KB = 1024.0;
	private static final double MB = 1048576.0;
	private static final double GB = 1073741824.0;

	/** 获取字符串宽度 */
	public static float GetTextWidth(String Sentence, float Size) {
		if (isEmpty(Sentence))
			return 0;
		TextPaint FontPaint = new TextPaint();
		FontPaint.setTextSize(Size);
		return FontPaint.measureText(Sentence.trim()) + (int) (Size * 0.1); // 留点余地
	}

	/**
	 * 拼接数组
	 * 
	 * @param array
	 * @param separator
	 * @return
	 */
	public static String join(final ArrayList<String> array,
			final String separator) {
		StringBuffer result = new StringBuffer();
		if (array != null && array.size() > 0) {
			for (String str : array) {
				result.append(str);
				result.append(separator);
			}
			result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	public static String join(final Iterator<String> iter,
			final String separator) {
		StringBuffer result = new StringBuffer();
		if (iter != null) {
			while (iter.hasNext()) {
				String key = iter.next();
				result.append(key);
				result.append(separator);
			}
			if (result.length() > 0)
				result.delete(result.length() - 1, result.length());
		}
		return result.toString();
	}

	/**
	 * 判断字符串是否为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * 
	 * @param str
	 * @return
	 */
	public static String trim(String str) {
		return str == null ? "" : str.trim();
	}

	/**
	 * 转换文件大小
	 * 
	 * @param size
	 * @return
	 */
	public static String generateFileSize(long size) {
		String fileSize;
		if(size < 0){
			size = 0 - size;
		}
		if (size < KB)
			fileSize = size + "B";
		else if (size < MB)
			fileSize = String.format("%.1f", size / KB) + "KB";
		else if (size < GB)
			fileSize = String.format("%.1f", size / MB) + "MB";
		else
			fileSize = String.format("%.1f", size / GB) + "GB";

		return fileSize;
	}

	/**
	 * 截取字符串
	 * 
	 * @param search
	 *            待搜索的字符串
	 * @param start
	 *            起始字符串 例如：<title>
	 * @param end
	 *            结束字符串 例如：</title>
	 * @param defaultValue
	 * @return
	 */
	public static String substring(String search, String start, String end,
			String defaultValue) {
		int start_len = start.length();
		int start_pos = StringUtils.isEmpty(start) ? 0 : search.indexOf(start);
		if (start_pos > -1) {
			int end_pos = StringUtils.isEmpty(end) ? -1 : search.indexOf(end,
					start_pos + start_len);
			if (end_pos > -1)
				return search.substring(start_pos + start.length(), end_pos);
			else
				return search.substring(start_pos + start.length());
		}
		return defaultValue;
	}

	/**
	 * 截取字符串
	 * 
	 * @param search
	 *            待搜索的字符串
	 * @param start
	 *            起始字符串 例如：<title>
	 * @param end
	 *            结束字符串 例如：</title>
	 * @return
	 */
	public static String substring(String search, String start, String end) {
		return substring(search, start, end, "");
	}

	/**
	 * 拼接字符串
	 * 
	 * @param strs
	 * @return
	 */
	public static String concat(String... strs) {
		StringBuffer result = new StringBuffer();
		if (strs != null) {
			for (String str : strs) {
				if (str != null)
					result.append(str);
			}
		}
		return result.toString();
	}

	/**
	 * 插入字符串
	 * @param a 原字符串
	 * @param b 插入字符串
	 * @param t 插入下标
	 * @return
	 */
	public static String insert(String a,String b,int t){
		return a.substring(0,t)+b+a.substring(t,a.length());
	}

	/** 获取中文字符个数 */
	public static int getChineseCharCount(String str) {
		String tempStr;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			if (tempStr.getBytes().length == 3) {
				count++;
			}
		}
		return count;
	}

	/** 获取英文字符个数 */
	public static int getEnglishCount(String str) {
		String tempStr;
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			tempStr = String.valueOf(str.charAt(i));
			if (!(tempStr.getBytes().length == 3)) {
				count++;
			}
		}
		return count;
	}

	/** 判断字符串是否有中文 */
	public static boolean checkChinese(String sequence) {
		final String format = "[\\u4E00-\\u9FA5\\uF900-\\uFA2D]";
		boolean result = false;
		Pattern pattern = Pattern.compile(format);
		Matcher matcher = pattern.matcher(sequence);
		result = matcher.find();
		return result;
	}

	/** 判断是否是英文 */
	public static boolean isEnglishString(String str) {
		for (int i = str.length(); --i >= 0;) {
			if ((str.charAt(i) >= 'a' && str.charAt(i) <= 'z')
					|| (str.charAt(i) >= 'A' && str.charAt(i) <= 'Z')) {
				continue;
			} else
				return false;
		}
		return true;
	}

	//判断是不是英文字母
	public static boolean isEnglish(String charaString) {
		return charaString.matches("^[a-zA-Z]*");
	}
}
