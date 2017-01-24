package com.cpapp.auth.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.auth.bean.MenuTreeViewBean;
import com.cpapp.auth.entity.AuthRole;
import com.cpapp.auth.service.IAuthRightService;
import com.cpapp.common.base.bean.QueryParamBean;

/*******************************************************************************
 * 系统角色权限____Controller
 ******************************************************************************/
@Controller
@RequestMapping("/admin/auth/")
public class RoleRightController {

	@Resource
	private IAuthRightService authRightService;

	/* 系统角色_列表 */
	@RequestMapping("/authRoleList")
	public ModelAndView authRoleList(AuthRole sysRole,
			QueryParamBean<AuthRole> pagin) {
		System.out.println("pagin = " + pagin);
		ModelAndView mav = new ModelAndView("/admin/auth/role/authRoleList");
		authRightService.searchSysRole(sysRole, pagin);
		mav.addObject("authRole", sysRole);
		mav.addObject("pagin", pagin);
		return mav;
	}

	/* 系统角色_基本信息 */
	@RequestMapping("/roleDetail")
	@ResponseBody
	public AuthRole RoleDetail(@RequestParam(value = "roleId") String roleId) {
		return authRightService.findSysRoleById(roleId);
	}

	/* 系统角色_修改 */
	@RequestMapping("/mergeSysRole")
	@ResponseBody
	public Map<String, Integer> mergeSysRole(AuthRole sysRole) {
		Map<String, Integer> data = new HashMap<String, Integer>(1);
		data.put("status", authRightService.saveOrUpdateSysRole(sysRole));
		return data;
	}

	/* 系统角色_删除 */
	@RequestMapping("/delSysRole")
	@ResponseBody
	public Integer deleteSysRole(@RequestParam(value = "roleId") String roleId) {
		authRightService.deleteSysRoles(roleId);
		return 0;
	}

	/* 系统角色_权限视图 */
	@RequestMapping("/roleRightView")
	public ModelAndView roleRightView(
			@RequestParam(value = "roleId") String roleId) {
		ModelAndView mav = new ModelAndView("/admin/auth/role/roleRightView");
		mav.addObject("authRole", authRightService.findSysRoleById(roleId));
		return mav;
	}

	/* 系统角色_权限数据 */
	@RequestMapping("/roleRightData")
	@ResponseBody
	public List<MenuTreeViewBean> roleRightData(
			@RequestParam(value = "roleId") String roleId) {
		return authRightService.findSysRoleRightData(roleId);
	}

	/* 系统角色_更新权限内容 */
	@RequestMapping("/updateRoleRight")
	@ResponseBody
	public Integer updateRoleRight(
			@RequestParam(value = "roleId") String roleId,
			@RequestParam(value = "menuIds", required = false) Long[] menuIds) {
		return authRightService.updateRoleRight(roleId, menuIds);
	}
}
