package com.cpapp.common.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.cpapp.common.constants.FileConfig;

/*******************************************************************************
 * 读取properties属性配置文件公共类
 * 
 * @author zengxiangtao
 * @version 2013-07-01
 ******************************************************************************/
public class LoadProperties {
	// 缓存起来
	private static Map<String, String> propertiesMap = null;

	protected LoadProperties() {
	}

	/** bkdy静态资源_路径 */
	public static String getStaticResourcePath() {
		return getByDefaultProperties("staticResourcePath");
	}

	/** bkdy静态资源_服务 */
	public static String getStaticResourceDomain() {
		return getByDefaultProperties("staticResourceDomain");
	}

	/** * 默认的配置资源文件 */
	public static String getByDefaultProperties(String key) {
		return getPropertieByKey(key, FileConfig.SYSCONFIG_PROPERTIES);
	}

	/**
	 * 根据键取出对应的值
	 * 
	 * @param key
	 *            需要从fileName资源文件中取值的 key
	 * @param fileName
	 *            资源文件名称
	 * @author zengxiangtao
	 */
	public static String getPropertieByKey(String key, String fileName) {
		InputStream inputStream = null;
		try {
			inputStream = LoadProperties.class.getResourceAsStream(fileName);
			Properties properties = new Properties();
			properties.load(inputStream);
			String strValue = properties.getProperty(key);
			return strValue;
		} catch (IOException ie) {
			ie.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	/**
	 * 根据键取出对应的值
	 * 
	 * @param key
	 *            需要从fileName资源文件中取值的 key
	 * @param fileName
	 *            资源文件名称
	 * @author zengxiangtao
	 */
	public static String getPropertieByKeyFromCache(String key, String fileName) {
		StringBuffer sb = new StringBuffer(50);
		if (StringUtils.isNotBlank(fileName)) {
			sb.append(fileName).append(".");
		}
		sb.append(key);
		String tempKey = sb.toString();
		// 从缓存的Map中取值
		if (null != propertiesMap && propertiesMap.containsKey(tempKey)) {
			return propertiesMap.get(tempKey);
		}
		String valueStr = getPropertieByKey(key, fileName);
		if (null == propertiesMap) {
			propertiesMap = new HashMap<String, String>();
		}
		propertiesMap.put(tempKey, valueStr);
		return valueStr;
	}

	/**
	 * 清除缓存cache
	 * 
	 * @author zengxiangtao
	 */
	public static void clearPropertiesCache() {
		if (null != propertiesMap) {
			propertiesMap.clear();
		}
	}

}
