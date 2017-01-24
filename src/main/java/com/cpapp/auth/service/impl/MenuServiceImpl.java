package com.cpapp.auth.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cpapp.auth.entity.Menu;
import com.cpapp.auth.service.IMenuService;
import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.base.service.BaseServiceImpl;

/*******************************************************************************
 * 系统菜单ServiceImpl
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
@Service
public class MenuServiceImpl extends BaseServiceImpl implements IMenuService {

	/** ----菜单检索列表---- */
	@Override
	public void searchMenu(Menu menu, Pagination<Menu> pagin) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
		if (null != menu) {
			// 菜单名称模糊查询
			if (StringUtils.isNotBlank(menu.getMenuName())) {
				criteria.add(Restrictions.like("menuName", menu.getMenuName(),
						MatchMode.ANYWHERE).ignoreCase());
			}
			// 菜单链接
			if (StringUtils.isNotBlank(menu.getLinkAddress())) {
				criteria.add(Restrictions.like("linkAddress",
						menu.getLinkAddress(), MatchMode.ANYWHERE).ignoreCase());
			}
			// 父ID
			if (null != menu.getParentId()) {
				criteria.add(Restrictions.eq("parentId", menu.getParentId()));
			}
			// 菜单类型
			if (null != menu.getMenuType()) {
				criteria.add(Restrictions.eq("menuType", menu.getMenuType()));
			}
		}
		criteria.addOrder(Order.desc("parentId"));
		criteria.addOrder(Order.desc("sortNum"));
		baseDAO.findByCriteria(criteria, pagin);
	}

	/** ----根据菜单ID查询菜单信息---- */
	@Override
	public Menu findMenuById(Long menuId) {
		return null == menuId ? null : baseDAO.findEntityById(Menu.class,
				menuId);
	}

	/** ----新增或修改菜单权限---- */
	@Override
	public void saveOrUpdateMenu(Menu menu) {
		if (null != menu && StringUtils.isNotBlank(menu.getMenuName())
				&& null != menu.getMenuType() && null != menu.getParentId()) {
			if (null == menu || null == menu.getMenuId()) {
				baseDAO.saveEntity(menu);
			} else {
				baseDAO.updateEntity(menu);
			}
		}
	}

	/** ----删除菜单权限---- */
	@Override
	public int deleteMenu(Long menuId) {
		// 判断有没有下属权限菜单
		DetachedCriteria criteria = DetachedCriteria.forClass(Menu.class);
		criteria.add(Restrictions.eq("parentId", menuId));
		criteria.setProjection(Projections.rowCount());
		List<Long> list = baseDAO.findByCriteria(criteria);
		if (null != list && list.size() > 0) {
			if (list.get(0) == 0) {
				baseDAO.deleteByHQL(Menu.class, "menuId", new Object[] { menuId });
				return 0;
			}
		}
		return -1;
	}

}
