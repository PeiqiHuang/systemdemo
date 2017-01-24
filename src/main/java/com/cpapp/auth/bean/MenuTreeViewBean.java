package com.cpapp.auth.bean;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * 角色权限Bean
 ******************************************************************************/
public class MenuTreeViewBean {

	private Long menuId;
	private String menuName;
	// 菜单类型[SYS(系统级):1;MEM(菜单级):2;BTN(按纽级):3]
	private Integer menuType;
	private String linkAddress;
	private Long parentId;
	// 节点是否选中
	private String checkFlag;
	// 是否关闭节点
	private String state="";
	private List<MenuTreeViewBean> children;

	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getCheckFlag() {
		return checkFlag;
	}

	public void setCheckFlag(String checkFlag) {
		this.checkFlag = checkFlag;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public List<MenuTreeViewBean> getChildren() {
		if (null == children) {
			children = new ArrayList<MenuTreeViewBean>();
		}
		return children;
	}

	public void setChildren(List<MenuTreeViewBean> children) {
		this.children = children;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "MenuTreeViewBean [menuId=" + menuId + ", menuName=" + menuName
				+ ", menuType=" + menuType + ", linkAddress=" + linkAddress
				+ ", parentId=" + parentId + ", checkFlag=" + checkFlag
				+ ", state=" + state + ", children=" + children + "]";
	}

}
