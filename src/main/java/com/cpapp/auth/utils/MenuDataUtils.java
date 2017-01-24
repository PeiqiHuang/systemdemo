package com.cpapp.auth.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cpapp.auth.bean.MenuTreeViewBean;

/*******************************************************************************
 * 菜单数据处理工具类
 ******************************************************************************/
public class MenuDataUtils {

	// 菜单数据装配成树型
	public static List<MenuTreeViewBean> assembleMenuData(
			List<MenuTreeViewBean> sortMenuData) {
		// 系统级
		Map<Long, MenuTreeViewBean> s_map = new HashMap<Long, MenuTreeViewBean>();
		// 菜单级
		Map<Long, MenuTreeViewBean> m_map = new HashMap<Long, MenuTreeViewBean>();
		List<MenuTreeViewBean> viewList = new ArrayList<MenuTreeViewBean>();
		for (MenuTreeViewBean mvb : sortMenuData) {
			// 系统级
			if (mvb.getMenuType() == 1) {
				s_map.put(mvb.getMenuId(), mvb);
				viewList.add(mvb);
			} else if (mvb.getMenuType() == 2) {
				// 菜单级
				m_map.put(mvb.getMenuId(), mvb);
				if (s_map.containsKey(mvb.getParentId())) {
					MenuTreeViewBean fatherNode = s_map.get(mvb.getParentId());
					if (null != fatherNode) {
						fatherNode.getChildren().add(mvb);
					}
				}
			} else {
				if (m_map.containsKey(mvb.getParentId())) {
					MenuTreeViewBean fatherNode = m_map.get(mvb.getParentId());
					if (null != fatherNode) {
						fatherNode.getChildren().add(mvb);
					}
				}
			}
		}
		return viewList;
	}
}
