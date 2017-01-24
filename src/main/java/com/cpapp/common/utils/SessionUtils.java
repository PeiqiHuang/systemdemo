package com.cpapp.common.utils;

import javax.servlet.http.HttpSession;

import com.cpapp.sys.entity.SysUser;

/*******************************************************************************
 * Session工具类
 ******************************************************************************/
public class SessionUtils {

	/* --获得登录验证码-- */
	public static String getLoginVerifyCode(HttpSession session) {
		if (null == session) {
			return null;
		}
		Object obj = session.getAttribute("loginVc");
		return null == obj ? null : (String) obj;
	}

	/* --session_添加登录用户-- */
	public static void setLoginUser(HttpSession session, SysUser su) {
		if (null != session) {
			session.setAttribute("loginUser", su);
		}
	}

	/* --session_移除登录用户-- */
	public static void removeLoginUser(HttpSession session) {
		if (null != session) {
			session.removeAttribute("loginUser");
		}
	}

	/* --session_获取登录用户-- */
	public static SysUser getSystemLoginUser(HttpSession session) {
		if (null == session) {
			return null;
		}
		Object obj = session.getAttribute("loginUser");
		return null == obj ? null : (SysUser) obj;
	}

	/* --session_获取登录用户-- */
	public static String getLoginUserSimpleInfo(SysUser su) {
		if (null == su) {
			return "{suId:0,loginName:''}";
		}
		return "{suId:'" + su.getSuId() + "',loginName:'" + su.getLoginName()
				+ "'}";
	}
}
