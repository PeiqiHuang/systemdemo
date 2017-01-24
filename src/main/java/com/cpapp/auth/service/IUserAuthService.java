package com.cpapp.auth.service;

import java.util.List;

import com.cpapp.auth.entity.Menu;

/*******************************************************************************
 * 用户权限Service_Interface
 * 
 * @version 2016-10-25
 ******************************************************************************/
public interface IUserAuthService {

	/*---- 用户系统级权限菜单 ----*/
	public List<Menu> findUserSysLevelMenu(Long suId);

	/*----用户子级菜单权限----*/
	public List<Menu> findSubMenu(Long suId, Long parentId);

	/*----用户权限验证----*/
	public boolean userRightValidate(Long suId, String accessURI);
}
