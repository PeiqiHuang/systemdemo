package com.cpapp.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.auth.entity.Menu;
import com.cpapp.auth.service.IUserAuthService;
import com.cpapp.common.utils.EncryptUitls;
import com.cpapp.common.utils.SessionUtils;
import com.cpapp.sys.entity.SysUser;
import com.cpapp.sys.service.ISysUserService;

/*******************************************************************************
 * 登录登出Controller
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
@Controller
@RequestMapping("/admin/sys/")
public class LoginController {

	@Resource
	private ISysUserService sysUserService;
	@Resource
	private IUserAuthService userAuthService;

	// 登录
	@RequestMapping("/login")
	public String login(@RequestParam(value = "loginName") String loginName,
			@RequestParam(value = "loginPwd") String loginPwd,
			@RequestParam(value = "loginVc") String loginVc, HttpSession session) {
		// 判断验证码
		String vcCode = SessionUtils.getLoginVerifyCode(session);
		if (StringUtils.isBlank(loginVc)
				|| !StringUtils.equalsIgnoreCase(loginVc, vcCode)) {
			session.setAttribute("msg", "验证码错误!");
			session.setAttribute("loginName", loginName);
			session.setAttribute("loginPwd", loginPwd);
			return "redirect:/index.jsp";
		}
		// 判断用户名密码
		SysUser su = sysUserService.findSysUserByLoginName(loginName);
		String pwdMd5 = EncryptUitls.MD5Digest(loginPwd);
		if (null == su || !StringUtils.equals(pwdMd5, su.getLoginPwd())) {
			session.setAttribute("msg", "用户名或密码不匹配!");
			return "redirect:/index.jsp";
		}
		session.removeAttribute("msg");
		session.removeAttribute("loginName");
		session.removeAttribute("loginPwd");
		SessionUtils.setLoginUser(session, su);
		return "redirect:/admin/sys/index.do";
	}

	// 菜单主页(系统级)
	@RequestMapping("/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView("/admin/index");
		SysUser su = SessionUtils.getSystemLoginUser(session);
		mav.addObject("menuLists",
				userAuthService.findUserSysLevelMenu(su.getSuId()));
		return mav;
	}

	/* 查询菜单的子菜单_后台管理展示 */
	@RequestMapping("/userSubMenuList")
	@ResponseBody
	public List<Menu> userSubMenuList(
			@RequestParam(value = "parentId") Long parentId, HttpSession session) {
		SysUser su = SessionUtils.getSystemLoginUser(session);
		return userAuthService.findSubMenu(su.getSuId(), parentId);
	}

	// 登出
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		SessionUtils.removeLoginUser(session);
		return "redirect:/index.jsp";
	}

	/* 修改登录密码 */
	@RequestMapping("/modifyLoginPwd")
	@ResponseBody
	public Map<String, Integer> modifyLoginPwd(
			@RequestParam(value = "oldPwd") String oldPwd,
			@RequestParam(value = "newPwd") String newPwd, HttpSession session) {
		SysUser loginUser = SessionUtils.getSystemLoginUser(session);
		Map<String, Integer> data = new HashMap<String, Integer>(1);
		int status = sysUserService.modifyUserPwd(oldPwd, newPwd, loginUser);
		data.put("status", status);
		return data;
	}
}
