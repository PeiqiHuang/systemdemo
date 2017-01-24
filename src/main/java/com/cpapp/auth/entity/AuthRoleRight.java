package com.cpapp.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************
 * 角色权限关联表(bk_sys_RoleRight)
 * 
 * @version 2016-10-25
 ******************************************************************************/
@Entity
@Table(name = "admin_roleRight")
public class AuthRoleRight {
	@Id
	@Column(name = "rrId")
	private String rrId;
	@Column(name = "roleId")
	private String roleId;
	@Column(name = "menuId")
	private String menuId;

	public String getRrId() {
		return rrId;
	}

	public void setRrId(String rrId) {
		this.rrId = rrId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}
}
