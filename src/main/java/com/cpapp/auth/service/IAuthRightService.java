package com.cpapp.auth.service;

import java.util.List;

import com.cpapp.auth.bean.MenuTreeViewBean;
import com.cpapp.auth.entity.AuthRole;
import com.cpapp.auth.entity.SysUserRole;
import com.cpapp.common.base.bean.QueryParamBean;

/*******************************************************************************
 * 系统用户权限Service_Interface
 ******************************************************************************/
public interface IAuthRightService {

	/*----角色检索----*/
	public void searchSysRole(AuthRole sysRole, QueryParamBean<AuthRole> pagin);

	/*---- 角色详情----*/
	public AuthRole findSysRoleById(String roleId);

	/*---- 更新角色信息----*/
	public int saveOrUpdateSysRole(AuthRole sysRole);

	/*---- 删除角色----*/
	public void deleteSysRoles(String roleId);

	/*---- 角色权限数据----*/
	public List<MenuTreeViewBean> findSysRoleRightData(String roleId);

	/*---- 更新角色权限内容----*/
	public int updateRoleRight(String roleId, Long[] menuIds);

	/*---- 系统用户角色_关联数据----*/
	public List<SysUserRole> findUserRoleData(Long userId);

	/*---- 系统用户_添加角色----*/
	public int saveUserRole(Long userId, String roleId);

	/*---- 系统用户_删除角色----*/
	public int deleteUserRoles(Long userId, String urId);

	/*---- 系统用户_重置用户权限----*/
	public void updateResetSysUserRight(Long userId);

	/*---- 用户权限数据----*/
	public List<MenuTreeViewBean> findUserRightData(Long userId);

	/*---- 更新用户权限(自定义微调)----*/
	public int updateUserDefinedRight(Long suId, Long[] menuIds);
}
