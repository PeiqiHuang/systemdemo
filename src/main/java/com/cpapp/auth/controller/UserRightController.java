package com.cpapp.auth.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.auth.bean.MenuTreeViewBean;
import com.cpapp.auth.service.IAuthRightService;
import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.utils.ServiceFacade;
import com.cpapp.sys.entity.SysUser;
import com.cpapp.sys.service.ISysUserService;

/*******************************************************************************
 * 系统用户权限____Controller
 ******************************************************************************/
@Controller
@RequestMapping("/admin/auth/")
public class UserRightController {
	@Resource
	private IAuthRightService authRightService;

	/* 用户权限分配 */
	@RequestMapping("/userList")
	public ModelAndView userList(SysUser user, Pagination<SysUser> pagin) {
		ModelAndView mav = new ModelAndView("/admin/auth/userRight/userList");
		ServiceFacade.getBean(ISysUserService.class).searchSysUser(user, pagin);
		mav.addObject("sysUser", user);
		mav.addObject("pagin", pagin);
		return mav;
	}

	/* 系统用户_角色列表 */
	@RequestMapping("/userRoleList")
	public ModelAndView userRoleList(@RequestParam(value = "suId") Long suId) {
		ModelAndView mav = new ModelAndView(
				"/admin/auth/userRight/userRoleList");
		mav.addObject("sysUser", ServiceFacade.getBean(ISysUserService.class)
				.findSysUserById(suId));
		mav.addObject("roleLists", authRightService.findUserRoleData(suId));
		return mav;
	}

	/* 系统用户_添加角色 */
	@RequestMapping("/addRole2User")
	@ResponseBody
	public Integer saveUserRole(@RequestParam(value = "suId") Long suId,
			@RequestParam(value = "roleId") String roleId) {
		int status = authRightService.saveUserRole(suId, roleId);
		/*if (status == 0) {
			// 重置权限
			final Long userId = suId;
			ServiceFacade.getTaskExecutor().execute(new Runnable() {
				@Override
				public void run() {
					authRightService.updateResetSysUserRight(userId);
				}
			});
		}*/
		return status;
	}

	/* 系统用户角色联_删除 */
	@RequestMapping("/delUserRole")
	@ResponseBody
	public Integer delUserRole(@RequestParam(value = "suId") Long suId,
			@RequestParam(value = "urId") String urId) {
		int status = authRightService.deleteUserRoles(suId, urId);
		/*if (status == 0) {
			// 重置权限
			final Long userId = suId;
			ServiceFacade.getTaskExecutor().execute(new Runnable() {
				@Override
				public void run() {
					authRightService.updateResetSysUserRight(userId);
				}
			});
		}*/
		return status;
	}

	/* 系统用户_权限视图 */
	@RequestMapping("/userRightView")
	public ModelAndView userRightView(@RequestParam(value = "suId") Long suId) {
		ModelAndView mav = new ModelAndView(
				"/admin/auth/userRight/userRightView");
		mav.addObject("sysUser", ServiceFacade.getBean(ISysUserService.class)
				.findSysUserById(suId));
		return mav;
	}

	/* 系统用户_权限数据 */
	@RequestMapping("/userRightData")
	@ResponseBody
	public List<MenuTreeViewBean> userRightData(
			@RequestParam(value = "suId") Long suId) {
		return authRightService.findUserRightData(suId);
	}

	/* 微调系统用户权限数据 */
	@RequestMapping("/updateUserDefinedRight")
	@ResponseBody
	public Integer updateUserDefinedRight(
			@RequestParam(value = "suId") Long suId,
			@RequestParam(value = "menuIds", required = false) Long[] menuIds) {
		return authRightService.updateUserDefinedRight(suId, menuIds);
	}
}
