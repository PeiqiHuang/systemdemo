package com.cpapp.auth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cpapp.auth.bean.MenuTreeViewBean;
import com.cpapp.auth.dao.AuthRightDAO;
import com.cpapp.auth.entity.AuthRole;
import com.cpapp.auth.entity.SysUserRole;
import com.cpapp.auth.service.IAuthRightService;
import com.cpapp.auth.utils.MenuDataUtils;
import com.cpapp.common.base.bean.QueryParamBean;
import com.cpapp.common.utils.SerialNumUtils;
import com.cpapp.common.utils.ServiceFacade;
import com.cpapp.sys.entity.SysUser;
import com.cpapp.sys.service.ISysUserService;

/*******************************************************************************
 * 系统用户权限ServiceImpl
 ******************************************************************************/
@Service
public class AuthRightServiceImpl implements IAuthRightService {

	@Resource(name = "authRightDAO")
	private AuthRightDAO authRightDAO;

	/** ----系统用户角色检索---- */
	@Override
	public void searchSysRole(AuthRole sr, QueryParamBean<AuthRole> pagin) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AuthRole.class);
		if (null != sr) {
			if (StringUtils.isNotBlank(sr.getRoleName())) {
				criteria.add(Restrictions.like("roleName",
						sr.getRoleName().trim(), MatchMode.ANYWHERE)
						.ignoreCase());
			}
			if (StringUtils.isNotBlank(sr.getRemark())) {
				criteria.add(Restrictions.like("remark", sr.getRemark().trim(),
						MatchMode.ANYWHERE).ignoreCase());
			}
		}
		criteria.addOrder(Order.desc("createTime"));
		authRightDAO.findByCriteria(criteria, pagin);
	}

	/** ---- 角色详情---- */
	@Override
	public AuthRole findSysRoleById(String roleId) {
		return StringUtils.isBlank(roleId) ? null : authRightDAO.findEntityById(
				AuthRole.class, roleId);
	}

	/** ---- 更新系统角色信息---- */
	@Override
	public int saveOrUpdateSysRole(AuthRole sr) {
		if (null == sr || StringUtils.isBlank(sr.getRoleName())) {
			return -1;
		}
		// 判断是否重名
		DetachedCriteria criteria = DetachedCriteria
				.forClass(AuthRole.class);
		criteria.add(Restrictions.like("roleName", sr.getRoleName().trim())
				.ignoreCase());
		List<AuthRole> list = authRightDAO.findByCriteria(criteria);
		if (null != list && list.size() > 0) { // 存在重名
			if (StringUtils.isBlank(sr.getRoleId())) {return -2;} // 新增时重名
			if (!StringUtils.equals(list.get(0).getRoleId(), sr.getRoleId())) {return -2;} // 修改时重名
		}
		if (StringUtils.isBlank(sr.getRoleId())) {
			sr.setRoleId(SerialNumUtils.generateUUID("ROLE"));
		}
		sr.setRoleName(sr.getRoleName().trim());
		sr.setCreateTime(new Date());
		if (StringUtils.isBlank(sr.getRoleId())) {
			authRightDAO.saveEntity(sr);
		} else {
			authRightDAO.updateEntity(sr);
		}
		return 0;
	}

	/** ---- 删除菜单角色---- */
	@Override
	public void deleteSysRoles(String roleId) {
		// 拥有该角色的所有用户的权限都要同步更新
		authRightDAO.updateRelatedSysUserRight(roleId, null);
		
		authRightDAO.deleteByHQL(AuthRole.class, "roleId",
				new String[] { roleId });
		authRightDAO.deleteRoleRightData(roleId);
	}

	/** ---- 菜单角色_权限数据---- */
	@Override
	public List<MenuTreeViewBean> findSysRoleRightData(String roleId) {
		List<MenuTreeViewBean> data = authRightDAO.findMenuRoleViewData(roleId);
		return MenuDataUtils.assembleMenuData(data);
	}

	/** ---- 更新角色权限内容---- */
	@Override
	public int updateRoleRight(String roleId, Long[] menuIds) {
		if (StringUtils.isBlank(roleId)) {
			return -1;
		}
		AuthRole sr = authRightDAO.findEntityById(AuthRole.class, roleId);
		if (null == sr) {
			return -2;
		}
		// 拥有该角色的所有用户的权限都要同步更新
		authRightDAO.updateRelatedSysUserRight(roleId, menuIds);
		
		// 清空原有权限数据
		authRightDAO.deleteRoleRightData(roleId);
		if (null != menuIds) {
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (Long menuId : menuIds) {
				batchArgs.add(new Object[] {
						SerialNumUtils.generateUUID("RRID"), roleId, menuId });
			}
			authRightDAO.saveRoleRightData(batchArgs);
		}
		return 0;
	}

	/** ---- 系统用户_角色---- */
	@Override
	public List<SysUserRole> findUserRoleData(Long userId) {
		return authRightDAO.findSysUserRoleData(userId);
	}

	/** ---- 系统用户_添加角色---- */
	@Override
	public int saveUserRole(Long userId, String roleId) {
		if (null == userId || StringUtils.isBlank(roleId)) {
			return -1;
		}
		SysUser su = ServiceFacade.getBean(ISysUserService.class)
				.findSysUserById(userId);
		AuthRole sysRole = authRightDAO.findEntityById(AuthRole.class, roleId);
		if (null == su || null == sysRole) {
			return -2;
		}
		// 判断角色是否存在
		SysUserRole ur = authRightDAO.findUserRole(userId, roleId);
		if (null == ur) {
			ur = new SysUserRole();
			ur.setUrId(SerialNumUtils.generateUUID("URID"));
		}
		ur.setRoleId(roleId);
		ur.setUserId(userId);
		authRightDAO.updateEntity(ur);
		// 删除用户已有的权限
		// authRightDAO.deleteUserRightData(userId);
		// 添加新增角色的权限到用户的权限中
		authRightDAO.saveUserRightData(userId, roleId);
		return 0;
	}

	/** ---- 删除用户角色关联---- */
	@Override
	public int deleteUserRoles(Long userId, String urId) {
		// 删除用户权限中被删角色的权限
		authRightDAO.deleteUserRightData(userId, urId);
		authRightDAO.deleteByHQL(SysUserRole.class, "urId",
				new String[] { urId });
		// 删除用户已有的权限
		//authRightDAO.deleteUserRightData(userId);
		return 0;
	}

	/** ---- 系统用户_重置用户权限---- */
	@Override
	public void updateResetSysUserRight(Long userId) {
		authRightDAO.saveResetUserRight(userId);
	}

	/** ---- 菜单角色_权限数据---- */
	@Override
	public List<MenuTreeViewBean> findUserRightData(Long userId) {
		List<MenuTreeViewBean> data = authRightDAO
				.findMenuUserRightViewData(userId);
		return MenuDataUtils.assembleMenuData(data);
	}

	/** ---- 更新用户权限(自定义微调)---- */
	@Override
	public int updateUserDefinedRight(Long userId, Long[] menuIds) {
		if (null == userId || null == menuIds) {
			return -1;
		}
		SysUser su = ServiceFacade.getBean(ISysUserService.class)
				.findSysUserById(userId);
		if (null == su) {
			return -2;
		}
		// 删除用户已有的权限
		authRightDAO.deleteUserRightData(userId);
		if (null != menuIds) {
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (Long menuId : menuIds) {
				batchArgs.add(new Object[] {
						SerialNumUtils.generateUUID("URID"), userId, menuId });
			}
			authRightDAO.saveUserRightData(batchArgs);
		}
		return 0;
	}
}
