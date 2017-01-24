package com.cpapp.auth.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.type.IntegerType;
import org.springframework.stereotype.Repository;

import com.cpapp.auth.entity.Menu;
import com.cpapp.common.base.dao.BaseDAOImpl;
import com.cpapp.common.base.hibernate.SimpleColumnToBean;

/*******************************************************************************
 * 用户权限视图与校验DAO
 ******************************************************************************/
@Repository(value = "userAuthRightDAO")
public class UserAuthRightDAO extends BaseDAOImpl {

	/* ---- 查找用户系统级菜单 ---- */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Menu> findUserSysLevelMenu(Long userId) {
		String sql = "select m.menuid,m.menuname,m.menutype,m.menuicon,m.parentId from admin_menu m left join admin_userright ur on ur.menuid = m.menuid where  ur.userid = ? and m.menutype=1  order by m.sortNum asc,m.menuName";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setResultTransformer(new SimpleColumnToBean(Menu.class));
		return query.list();
	}

	/* ---- 查找用户系统级菜单 ---- */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Menu> findUserRightSubMenu(Long userId, Long parentId) {
		String sql = "select m.menuid,m.menuname,m.menutype,m.menuicon,m.linkAddress,m.parentId from admin_menu m left join admin_userright ur on ur.menuid = m.menuid where  ur.userid = ?  and m.parentId = ?   order by m.sortNum asc,m.menuName";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setLong(1, parentId);
		query.setResultTransformer(new SimpleColumnToBean(Menu.class));
		return query.list();
	}

	/*----用户权限验证----*/
	public boolean userRightValidate(Long userId, String accessURI) {
		String sql = " select count(*) as count  from admin_menu m left join admin_userright ur on ur.menuid = m.menuid where ur.userid = ? and m.linkaddress= ? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setLong(0, userId);
		query.setParameter(1, accessURI);
		// 查询总行数
		query.addScalar("count", IntegerType.INSTANCE);
		Integer totalRows = (Integer) query.uniqueResult();
		return totalRows > 0;
	}
}
