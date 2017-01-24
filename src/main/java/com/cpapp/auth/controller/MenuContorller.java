package com.cpapp.auth.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cpapp.auth.entity.Menu;
import com.cpapp.auth.service.IMenuService;
import com.cpapp.common.base.bean.Pagination;

/*******************************************************************************
 * 菜单URI____Controller
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
@Controller
@RequestMapping("/admin/auth/menu/")
public class MenuContorller {

	@Resource
	private IMenuService menuService;

	/* 菜单检索 */
	@RequestMapping("/menuList")
	public ModelAndView menuList(Menu menu, Pagination<Menu> pagin) {
		ModelAndView mav = new ModelAndView("/admin/auth/menu/menuList");
		menuService.searchMenu(menu, pagin);
		mav.addObject("menu", menu);
		mav.addObject("pagin", pagin);
		return mav;
	}

	/** 菜单修改页 */
	@RequestMapping("/menuDisplay")
	public ModelAndView menuDisplay(
			@RequestParam(value = "menuId", required = false) Long menuId) {
		ModelAndView mav = new ModelAndView("/admin/auth/menu/menuDisplay");
		mav.addObject("menu", menuService.findMenuById(menuId));
		return mav;
	}

	/* 权限菜单 新增或修改 */
	@RequestMapping("/mergeMenu")
	@ResponseBody
	public Long mergeMenu(Menu menu) {
		menuService.saveOrUpdateMenu(menu);
		return null == menu ? null : menu.getMenuId();
	}

	/* 权限菜单 删除 */
	@RequestMapping("/deleteMenu")
	@ResponseBody
	public Integer deleteMenu(
			@RequestParam(value = "menuId", required = true) Long menuId) {
		return menuService.deleteMenu(menuId);
	}
}
