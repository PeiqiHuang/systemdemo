package com.cpapp.crud.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cpapp.crud.service.ICommonCRUDService;

/*******************************************************************************
 * 系统角色权限____Controller
 ******************************************************************************/
@Controller
@RequestMapping("/crud/")
public class CommonCRUDController {

	//@Resource
	//private ICommonCRUDService CommonCRUDService;

	/* 系统角色_列表 */
	/*@RequestMapping("/list")
	public ModelAndView list(CommonTable commonTable, String table,
			QueryParamBean<AuthRole> pagin) {
		ModelAndView mav = new ModelAndView("/common/list");
		commonTableService.searchCommonTable(commonTable, pagin);
		mav.addObject("commonTable", commonTable);
		mav.addObject("pagin", pagin);
		return mav;
	}*/
}
