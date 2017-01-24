package com.cpapp.auth.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cpapp.auth.dao.UserAuthRightDAO;
import com.cpapp.auth.entity.Menu;
import com.cpapp.auth.service.IUserAuthService;
import com.cpapp.common.constants.SysConfig;

/*******************************************************************************
 * 用户权限(视图与校验)ServiceImpl
 ******************************************************************************/
@Service
public class UserAuthServiceImpl implements IUserAuthService {

	@Resource(name="userAuthRightDAO")
	private UserAuthRightDAO userAuthRightDAO;

	/** ---- 用户系统级权限菜单 ---- */
	@Override
	public List<Menu> findUserSysLevelMenu(Long suId) {
		if (null == suId) {
			return null;
		}
		// 判断是否超级管理员
		if (suId.longValue() != SysConfig.SYS_ADMIN_USERID) {
			return userAuthRightDAO.findUserSysLevelMenu(suId);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
		criteria.add(Restrictions.eq("menuType", 1));
		criteria.addOrder(Order.asc("sortNum"));
		criteria.addOrder(Order.desc("menuName"));
		return userAuthRightDAO.findByCriteria(criteria);
	}

	/** ---- 查找菜单所属下级菜单 ---- */
	@Override
	public List<Menu> findSubMenu(Long suId, Long parentId) {
		if (null == suId) {
			return null;
		}
		// 判断是否超级管理员
		if (suId.longValue() != SysConfig.SYS_ADMIN_USERID) {
			return userAuthRightDAO.findUserRightSubMenu(suId, parentId);
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
		criteria.add(Restrictions.eq("parentId", parentId));
		criteria.addOrder(Order.asc("sortNum"));
		criteria.addOrder(Order.desc("menuName"));
		return userAuthRightDAO.findByCriteria(criteria);
	}

	@Override
	public boolean userRightValidate(Long suId, String accessURI) {
		if (null == suId || StringUtils.isBlank(accessURI)) {
			return false;
		}
		// 判断是否超级管理员
		if (suId.longValue() != SysConfig.SYS_ADMIN_USERID) {
			return userAuthRightDAO.userRightValidate(suId, accessURI);
		}
		return true;
	}

}
