package com.cpapp.common.utils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import com.cpapp.common.constants.FileConfig;
import com.cpapp.common.constants.SysConfig;


/*******************************************************************************
 * 请求参数签名工具类
 * 
 * @author zengxiangtao
 ******************************************************************************/
public class SignUtils {
	/**
	 * requestParams请求参数的转换
	 * 
	 * @param params
	 *            请求的参数
	 * @return 验证结果
	 */
	public static Map<String, String> conversion(Map<String, String[]> map) {
		if (null == map || map.size() <= 0) {
			return null;
		}
		Map<String, String> params = new HashMap<String, String>(map.size());
		for (Iterator<String> iter = map.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			if (!SysConfig.CP_SIGN.equals(name)) {
				String[] values = (String[]) map.get(name);
				String valueStr = "";
				for (int i = 0; i < values.length; i++) {
					valueStr = (i == values.length - 1) ? valueStr + values[i]
							: valueStr + values[i] + ",";
				}
				params.put(name, valueStr);
			}
		}
		return params;
	}

	/**
	 * 签名字符串
	 * 
	 * @param text
	 *            需要签名的字符串
	 * @return 签名结果
	 */
	public static String signParam(String text, String signKey) {
		if (StringUtils.isBlank(text)) {
			return null;
		}
		text = text + signKey;
		return DigestUtils.md5Hex(text.getBytes(Charset
				.forName(SysConfig.SYS_CHARTSET)));
	}

	/**
	 * 参数组装成一个字符串
	 * 
	 * @param params
	 *            请求参数
	 * @param filterParam
	 *            需要过滤的参数名
	 * @return linkStr
	 */
	public static String createLinkStr(Map<String, String> params,
			List<String> filterParam) {
		// 没有包含签名;或者签名值为空
		if (null == params || params.size() <= 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		List<String> kl = new ArrayList<String>(params.keySet());
		// 排序
		Collections.sort(kl);
		for (String key : kl) {
			String value = params.get(key);
			// 非空值
			if (StringUtils.isNotBlank(value)) {
				// 非过滤参数
				if (null == filterParam || !filterParam.contains(key)) {
					sb.append(key).append("=").append(value).append("&");
				}
			}
		}
		return sb.substring(0, sb.length() - 1);
	}

	/**
	 * 获得参数字符串
	 * 
	 * @param params
	 *            请求参数
	 * @return linkStr
	 */
	public static String createReqStrWithoutSign(Map<String, String> params) {
		List<String> filterParam = new ArrayList<String>(1);
		filterParam.add(SysConfig.CP_SIGN);
		return createLinkStr(params, filterParam);
	}

	/**
	 * 根据访问令牌获得密钥
	 * 
	 * @param merToken
	 *            令牌
	 * @return merSignKey
	 */
	public static String getSignKey(String token) {
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return LoadProperties.getPropertieByKey(token, FileConfig.SYSCONFIG_PROPERTIES);
	}
}
