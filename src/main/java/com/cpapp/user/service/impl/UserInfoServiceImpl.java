package com.cpapp.user.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.base.service.BaseServiceImpl;
import com.cpapp.user.entity.User;
import com.cpapp.user.dao.UserInfoDAO;
import com.cpapp.user.service.IUserInfoService;


/*******************************************************************************
 * 用户列表ServiceImpl
 * 
 * @author wuxiaorui
 * @version 2016-12-05
 ******************************************************************************/
@Service
public class UserInfoServiceImpl extends BaseServiceImpl implements
		IUserInfoService {
	@Resource(name = "userInfoDAO")
	private UserInfoDAO userInfoDAO;

	/** ----用户信息检索---- */
	@Override
	public void searchUserInfo(User user, Pagination<User> pagin) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		if (null != user) {
			// 用户手机号
			if (StringUtils.isNotBlank(user.getMobilePhone())) {
				criteria.add(Restrictions.like("mobilePhone",
						user.getMobilePhone().trim(), MatchMode.ANYWHERE)
						.ignoreCase());
			}
			// 用户ID
			if (StringUtils.isNotBlank(user.getUserId())) {
				criteria.add(Restrictions.like("userId",
						user.getUserId().trim(), MatchMode.ANYWHERE)
						.ignoreCase());
			}			
			//  用户ID
//			if (null != user.getUserId()) {
//				criteria.add(Restrictions.eq("userId",
//						user.getUserId()));
//			}
		}
		
		criteria.addOrder(Order.desc("createTime"));
		baseDAO.findByCriteria(criteria, pagin);
	}

	/** 根据用户ID查找用户基本信息 */
	@Override
	public User findUserInfoById(String userId) {
		return null == userId ? null : baseDAO
				.findEntityById(User.class, userId);
	}

	/** ---- 根据用户ID查找用户详细信息---- */
	@Override
	public User findUserDetail(String userId) {
		return userInfoDAO.findUserDetail(userId);
	}
}
