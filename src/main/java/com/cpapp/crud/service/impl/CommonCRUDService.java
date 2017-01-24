package com.cpapp.crud.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.cpapp.auth.entity.AuthRole;
import com.cpapp.common.base.bean.QueryParamBean;
import com.cpapp.crud.dao.CommonCRUDDAO;
import com.cpapp.crud.service.ICommonCRUDService;

public class CommonCRUDService implements ICommonCRUDService {

	@Resource(name = "commonCRUDDAO")
	private CommonCRUDDAO commonCRUDDAO;
	
	@Override
	public void searchSysRole(Class c, String pattern, QueryParamBean pagin) {
		/*DetachedCriteria criteria = DetachedCriteria.forClass(c);
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
		commonCRUDDAO.findByCriteria(criteria, pagin);*/

	}

}
