package com.cpapp.common.constants;

/*******************************************************************************
 * 系统常量
 * 
 * @author zengxiangtao
 * @version 2016-04-16
 ******************************************************************************/
public interface SysConfig {

	// 系统默认编码
	public static final String SYS_CHARTSET = "UTF-8";
	// 系统超级管理员
	public static final long SYS_ADMIN_USERID = 1l;
	// 换行符
	public static final String LINE_FEED = "\r\n";
	// 日期格式
	public static final String[] DATE_PATTERN = new String[] { "yyyyMMdd",
			"yyyy-MM-dd", "yyyy/MM/dd" };
	// 时间格式
	public static final String[] TIME_PATTERN = new String[] {
			"yyyyMMddHHmmss", "yyyy-MM-dd HH:mm:ss", "yyyy/MM/dd HH:mm:ss" };
	// 手机号正则表达式
	public static final String TELEPHONE_PATTERN = "1[3|4|5|7|8][0-9]\\d{8}";
	// 数字字符串常量
	public final static String DIGITALS = "0123456789";

	// 访问令牌
	public static final String CP_TOKEN = "cp_token";
	// 访问签名
	public static final String CP_SIGN = "cp_signature";
	// 访问时间戳
	public static final String CP_TIMESTAMP = "cp_timestamp";

}
