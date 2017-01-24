package com.cpapp.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************
 * 用户权限关联表(bk_sys_userRight)
 * 
 * @version 2016-10-25
 ******************************************************************************/
@Entity
@Table(name = "admin_userright")
public class SysUserRight {

	@Id
	@Column(name = "urId")
	private String urId;
	@Column(name = "userId")
	private String userId;
	@Column(name = "menuId")
	private String menuId;

	public String getUrId() {
		return urId;
	}

	public void setUrId(String urId) {
		this.urId = urId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
