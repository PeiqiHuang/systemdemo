package com.cpapp.auth.service;

import com.cpapp.auth.entity.Menu;
import com.cpapp.common.base.bean.Pagination;

/*******************************************************************************
 * 系统菜单Service_Interface
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
public interface IMenuService {
	/*----菜单检索列表----*/
	public void searchMenu(Menu menu, Pagination<Menu> pagin);

	/*----根据菜单ID查询菜单信息----*/
	public Menu findMenuById(Long menuId);

	/*----新增或修改菜单权限----*/
	public void saveOrUpdateMenu(Menu menu);

	/*----删除菜单权限----*/
	public int deleteMenu(Long menuId);
}
