package com.cpapp.common.utils;

import java.nio.charset.Charset;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;


/*******************************************************************************
 * 加密工具类
 * 
 * @author zengxiangtao
 * @version 2013-07-01
 ******************************************************************************/
public class EncryptUitls {

	protected EncryptUitls() {

	}

	/***************************************************************************
	 * MD5<br>
	 * 
	 * @param data
	 *            原文<br>
	 **************************************************************************/
	public static String MD5Digest(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return DigestUtils.md5Hex(str.getBytes(Charset.forName("UTF-8")));
	}

	/***************************************************************************
	 * BASE64 编码<br>
	 * 
	 * @param data
	 **************************************************************************/
	public static String base64Encode(String data) {
		if (StringUtils.isBlank(data)) {
			return null;
		}
		try {
			String rs =new String(Base64.encodeBase64String(data
					.getBytes()));
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/***************************************************************************
	 * BASE64 解码<br>
	 * 
	 * @param data
	 **************************************************************************/
	public static String base64Decode(String key) {
		try {
			String rs =  new String(Base64.decodeBase64(key));
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
