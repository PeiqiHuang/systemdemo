package com.cpapp.user.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.utils.SessionUtils;
import com.cpapp.user.entity.User;

import com.cpapp.user.service.IUserInfoService;

/*******************************************************************************
 * 用户管理____Controller
 * 
 * @author wuxiaorui
 * @version 2016-12-05
 ******************************************************************************/
@Controller
@RequestMapping("/admin/user/userinfo/")
public class UserInfoController {

	@Resource
	private IUserInfoService UserInfoService;

	/* 用户检索 */
	@RequestMapping("/userInfoList")
	public ModelAndView userList(User user, Pagination<User> pagin) {
		ModelAndView mav = new ModelAndView("/admin/user/userInfo/userInfoList");
		UserInfoService.searchUserInfo(user, pagin);
		mav.addObject("user", user);
		mav.addObject("pagin", pagin);
		return mav;
	}

	/* 用户查看详细页 */
	@RequestMapping("/userInfoDisplay")
	public ModelAndView userDisplay(
			@RequestParam(value = "userId", required = false) String userId) {
		ModelAndView mav = new ModelAndView("/admin/user/userInfo/userInfoDisplay");
		//mav.addObject("user", UserInfoService.findUserInfoById(userId));
		mav.addObject("user", UserInfoService.findUserDetail(userId));
		return mav;
	}
}
