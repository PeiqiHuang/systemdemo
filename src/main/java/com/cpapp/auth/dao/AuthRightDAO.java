package com.cpapp.auth.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import com.cpapp.auth.bean.MenuTreeViewBean;
import com.cpapp.auth.entity.AuthRoleRight;
import com.cpapp.auth.entity.SysUserRole;
import com.cpapp.common.base.dao.BaseDAOImpl;
import com.cpapp.common.base.hibernate.SimpleColumnToBean;
import com.cpapp.common.utils.SerialNumUtils;

/*******************************************************************************
 * 系统权限DAO
 ******************************************************************************/
@Repository(value = "authRightDAO")
public class AuthRightDAO extends BaseDAOImpl {

	/*---- 角色_权限_数据视图---- */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<MenuTreeViewBean> findMenuRoleViewData(String roleId) {
		String sql = "select t1.menuid,t1.menuname,t1.parentId,t1.menuType,t1.linkAddress,IFNULL(t2.rrid,0) as checkFlag from admin_menu t1 left join (select menuId,rrId,roleId from admin_roleRight where roleId = ? ) t2 on t1.menuid = t2.menuid order by t1.menutype asc,t1.sortnum desc,t1.createTime desc ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setParameter(0, roleId);
		query.setResultTransformer(new SimpleColumnToBean(
				MenuTreeViewBean.class));
		return query.list();
	}
	
	/*---- 角色_权限_数据---- */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<AuthRoleRight> findRoleRightData(String roleId) {
		String sql = "select * from admin_roleright where roleid = ?";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setParameter(0, roleId);
		query.setResultTransformer(new SimpleColumnToBean(
				AuthRoleRight.class));
		return query.list();
	}

	/*---- 清空角色的权限---- */
	public void deleteRoleRightData(String roleId) {
		String sql = "delete from admin_roleRight where roleId = ? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setParameter(0, roleId);
		query.executeUpdate();
	}

	/*---- 更新角色的权限---- */
	public void saveRoleRightData(List<Object[]> batchArgs) {
		String sql = "insert into admin_roleRight(rrid, roleid, menuid) values(?,?,?)";
		excuteBatchData(sql, batchArgs);
	}

	/*---- 用户_角色_关联数据---- */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysUserRole> findSysUserRoleData(Long userId) {
		String sql = "select ur.urid,r.roleId,r.rolename,r.remark as roleRemark from admin_userrole ur left join admin_role r on r.roleid = ur.roleid where ur.userid =? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setResultTransformer(new SimpleColumnToBean(SysUserRole.class));
		return query.list();
	}
	
	/*---- 角色_用户_关联数据---- */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<SysUserRole> findSysUserRoleDataByRoleId(String roleId) {
		String sql = "select ur.urid,ur.userid,r.roleId,r.rolename,r.remark as roleRemark from admin_userrole ur left join admin_role r on r.roleid = ur.roleid where r.roleid =? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setString(0, roleId);
		query.setResultTransformer(new SimpleColumnToBean(SysUserRole.class));
		return query.list();
	}

	/*---- 用户_角色---- */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public SysUserRole findUserRole(Long userId, String roleId) {
		String sql = "select  Urid, Userid, Roleid from admin_userrole where userId =? and roleId= ? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setString(1, roleId);
		query.setResultTransformer(new SimpleColumnToBean(SysUserRole.class));
		List<SysUserRole> list = query.list();
		return null == list || list.size() < 1 ? null : list.get(0);
	}

	/*---- 清空角色的权限---- */
	public void deleteUserRightData(Long userId) {
		String sql = "delete from admin_userright where userId = ? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setParameter(0, userId);
		query.executeUpdate();
	}

	/*---- 重置用户的权限---- */
	public void saveResetUserRight(Long userId) {
		StringBuffer sql = new StringBuffer(
				" insert into admin_userright(urid, userid, menuid) select CONCAT('URID', REPLACE(UUID(),'-','')) ,");
		sql.append("'").append(userId).append("',");
		sql.append(" menuId from ( select distinct rr.menuid as menuId from admin_roleright rr left join admin_userrole ur on rr.roleid = ur.roleid where ur.userid = ? ) ");
		SQLQuery query = currentSession().createSQLQuery(sql.toString());
		query.setParameter(0, userId);
		query.executeUpdate();
	}

	/*---- 用户_权限_数据视图---- */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<MenuTreeViewBean> findMenuUserRightViewData(Long userId) {
		String sql = "select t1.menuid,t1.menuname,t1.parentId,t1.menuType,t1.linkAddress,IFNULL(t2.urid,0) as checkFlag from admin_menu t1 left join (select menuId,urId,userId from admin_userright where userId = ? ) t2 on t1.menuid = t2.menuid order by t1.menutype asc,t1.sortnum desc,t1.createTime desc ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setResultTransformer(new SimpleColumnToBean(
				MenuTreeViewBean.class));
		return query.list();
	}

	/*---- 更新用户的权限---- */
	public void saveUserRightData(List<Object[]> batchArgs) {
		String sql = "insert into admin_userright(urid, userid, menuid)values(?,?,?)";
		excuteBatchData(sql, batchArgs);
	}

	/*---- 更新用户的权限---- */
	public void saveUserRightData(Long userId, String roleId) {
		StringBuffer sql = new StringBuffer(
				" insert into admin_userright(urid, userid, menuid) select CONCAT('URID', REPLACE(UUID(),'-','')) ,");
		sql.append("'").append(userId).append("',");
		sql.append(" m.menuid from ( select distinct menuid from admin_roleright where roleid = ? ");
		sql.append(" and menuid not in (select distinct menuid from admin_userright where userid = ?) ) m");
		SQLQuery query = currentSession().createSQLQuery(sql.toString());
		query.setParameter(0, roleId);
		query.setLong(1, userId);
		query.executeUpdate();
	}
	
	/*---- 删除用户的权限---- */
	public void deleteUserRightData(List<Object[]> batchArgs) {
		String sql = "delete from admin_userright where userId = ? and menuId = ?";
		excuteBatchData(sql, batchArgs);
	}
	
	/**
	 * 删除用户角色后删除用户对应的权限
	 * @param userId
	 * @param rId admin_userrole的urId或者roleId
	 */
	public void deleteUserRightData(Long userId, String rId) {
		String sql = "delete from admin_userright where userId = ? and menuId in (select rr.menuId from admin_userrole ur, admin_roleRight rr where ur.roleId = rr.roleId and (urId = ? or ur.roleId = ?))";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setParameter(0, userId);
		query.setParameter(1, rId);
		query.setParameter(2, rId);
		query.executeUpdate();
		
		// 上面有可能把还有子菜单的父菜单给删掉
		resetUserRightParent(userId);
	}
	
	/* 把子权限在userright里的权限也插进表里 */
	private void resetUserRightParent(Long userId) {
		StringBuilder sql = new StringBuilder();
		sql.append(" insert into admin_userright(urid, userid, menuid) select CONCAT('URID', REPLACE(UUID(),'-','')) ,");
		sql.append("'").append(userId).append("', t.menuid from ");
		sql.append("(select distinct m.parentid as menuid from admin_userright ur, admin_menu m where ur.menuid = m.menuid and ur.userid = :userid and m.parentid > 0 and m.parentid not in ");
		sql.append("(select menuid from admin_userright where userid = :userid) ) t");
		SQLQuery query = currentSession().createSQLQuery(sql.toString());
		query.setParameter("userid", userId);
		int result = query.executeUpdate();
	}

	/*---- 更新角色关联用户的权限---- */
	/**
	 * 
	 * @param roleId 
	 * @param menuIds 新的权限菜单ids
	 */
	public void updateRelatedSysUserRight(String roleId, Long[] menuIds) {
		// 角色修改前权限
		List<AuthRoleRight> rrList = findRoleRightData(roleId);
		List<Long> oldMenuIdsList = new ArrayList<Long>();
		List<Long> newMenuIdsList = new ArrayList<Long>();
		for(AuthRoleRight rr : rrList) {
			oldMenuIdsList.add(Long.parseLong(rr.getMenuId()));
		}
		if(null != menuIds) {
			Collections.addAll(newMenuIdsList, menuIds);
		}
		// 对比得出新增和删除的权限
		List<Long> addList = new ArrayList<Long>();
		List<Long> delList = new ArrayList<Long>();
		for(Long newMenuId : newMenuIdsList) {
			if(!oldMenuIdsList.contains(newMenuId)) {
				addList.add(newMenuId);
			}
		}
		for(Long oldMenuId : oldMenuIdsList) {
			if(!newMenuIdsList.contains(oldMenuId)) {
				delList.add(oldMenuId);
			}
		}
		// 关联用户权限增加新的和删除旧的
		List<SysUserRole> userList = findSysUserRoleDataByRoleId(roleId);
		for (SysUserRole sysUserRole : userList) {
			Long userId = sysUserRole.getUserId();
			// 插入角色新增的权限
			List<Object[]> batchArgs = new ArrayList<Object[]>();
			for (Long menuId : addList) {
				batchArgs.add(new Object[] {
						SerialNumUtils.generateUUID("URID"), userId, menuId });
			}
			saveUserRightData(batchArgs);
			// 删除角色已删除的权限
			batchArgs = new ArrayList<Object[]>();
			for (Long menuId : delList) {
				batchArgs.add(new Object[] {userId, menuId });
			}
			deleteUserRightData(batchArgs);
		}
	}

}
