package com.cpapp.user.service;

import java.util.List;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.user.entity.User;

/*******************************************************************************
 * 用户管理Service_Interface
 * 
 * @author wuxiaorui
 * @version 2016-12-05
 ******************************************************************************/
public interface IUserInfoService {
	
	/*---- 根据用户ID查找用户信息 ----*/	
	public User findUserInfoById(String userId);

	/*---- 根据用户ID查找用户详细信息----*/
	public User findUserDetail(String userId);
	
	/*----用户信息检索----*/
	public void searchUserInfo(User user, Pagination<User> pagin);


}
