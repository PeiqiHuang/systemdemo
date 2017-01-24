package com.cpapp.sys.service;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.sys.entity.SysUser;

/*******************************************************************************
 * 系统用户Service_Interface
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
public interface ISysUserService {
	
	/*---- 根据登录名查找登录用户 ----*/
	public SysUser findSysUserByLoginName(String loginName);

	/*----系统用户检索----*/
	public void searchSysUser(SysUser user, Pagination<SysUser> pagin);

	/*---- 根据用户ID----*/
	public SysUser findSysUserById(Long suId);

	/*---- 更新系统用户信息----*/
	public int saveOrUpdateSysUser(SysUser su, SysUser modifier);

	/*---- 重置系统用户密码----*/
	public void updateResetUserPwd(Long suId, SysUser modifier);

	/*---- 修改系统用户密码----*/
	public int modifyUserPwd(String oldPwd, String newPwd, SysUser modifier);
}
