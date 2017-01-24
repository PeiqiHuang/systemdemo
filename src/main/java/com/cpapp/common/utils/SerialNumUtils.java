package com.cpapp.common.utils;

import java.util.UUID;

/*******************************************************************************
 * 流水号工具类
 * 
 * @author zengxiangtao
 * @version 2013-07-01
 ******************************************************************************/
public class SerialNumUtils {

	/**
	 * Description :generateCheckCode 随机产生4位验证码
	 * 
	 * @return char[] 随机字符的字符数组
	 * @author zengxiangtao
	 */
	public static char[] generateCheckCode(int length) {
		if (length > 0) {
			// 定义验证码的字符表
			String chars = "23456789ABCDEFGHJKLMNPQRSTUVWXYZ";
			char[] rands = new char[length];
			// 随机产生4位数的字符
			for (int i = 0; i < length; i++) {
				int rand = (int) (Math.random() * 32);
				rands[i] = chars.charAt(rand);
			}
			return rands;
		}
		return null;
	}

	/**
	 * Description :generateCheckCode 随机产生4位验证码
	 * 
	 * @return char[] 随机字符的字符数组
	 * @author zengxiangtao
	 */
	public static char[] generateNumberCode(int length) {
		if (length > 0) {
			// 定义验证码的字符表
			String chars = "2345678901";
			char[] rands = new char[length];
			// 随机产生4位数的字符
			for (int i = 0; i < length; i++) {
				int rand = (int) (Math.random() * 10);
				rands[i] = chars.charAt(rand);
			}
			return rands;
		}
		return null;
	}

	/*** 32位UUID */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	/*** 36位UUID */
	public static String generateUUID(String startText) {
		return startText + UUID.randomUUID().toString().replace("-", "");
	}
}
