package com.cpapp.crud.dao;

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
@Repository(value = "commonCRUDDAO")
public class CommonCRUDDAO extends BaseDAOImpl {

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

}
