package com.cpapp.sys.controller;

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
import com.cpapp.sys.entity.SysUser;
import com.cpapp.sys.service.ISysUserService;

/*******************************************************************************
 * 系统用户____Controller
 * 
 * @author zengxiangtao
 * @version 2016-04-21
 ******************************************************************************/
@Controller
@RequestMapping("/admin/sys/user/")
public class SysUserController {

	@Resource
	private ISysUserService sysUserService;

	/* 用户检索 */
	@RequestMapping("/userList")
	public ModelAndView userList(SysUser user, Pagination<SysUser> pagin) {
		ModelAndView mav = new ModelAndView("/admin/sys/sysUser/userList");
		sysUserService.searchSysUser(user, pagin);
		mav.addObject("user", user);
		mav.addObject("pagin", pagin);
		return mav;
	}

	/* 系统用户新增/修改页 */
	@RequestMapping("/userDisplay")
	public ModelAndView userDisplay(
			@RequestParam(value = "suId", required = false) Long suId) {
		ModelAndView mav = new ModelAndView("/admin/sys/sysUser/userDisplay");
		mav.addObject("sysUser", sysUserService.findSysUserById(suId));
		return mav;
	}

	/* 系统用户 新增或修改 */
	@RequestMapping("/mergeUser")
	@ResponseBody
	public Map<String, Object> mergeUser(SysUser su, HttpSession session) {
		SysUser loginUser = SessionUtils.getSystemLoginUser(session);
		Map<String, Object> data = new HashMap<String, Object>(2);
		int status = sysUserService.saveOrUpdateSysUser(su, loginUser);
		data.put("status", status);
		data.put("suId", su.getSuId());
		return data;
	}

	/* 重置用户密码 */
	@RequestMapping("/resetUserPwd")
	@ResponseBody
	public String resetUserPwd(@RequestParam(value = "suId") Long suId,
			HttpSession session) {
		sysUserService.updateResetUserPwd(suId,
				SessionUtils.getSystemLoginUser(session));
		return "SUCCESS";
	}
}
