package com.cpapp.auth.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/*******************************************************************************
 * 用户角色关联表(bk_sys_userRole)
 * 
 * @version 2016-10-25
 ******************************************************************************/
@Entity
@Table(name = "admin_userrole")
public class SysUserRole {
	@Id
	@Column(name = "urId")
	private String urId;
	@Column(name = "userId")
	private Long userId;
	@Column(name = "roleId")
	private String roleId;
	@Transient
	private String roleName;
	@Transient
	private String roleRemark;

	public String getUrId() {
		return urId;
	}

	public void setUrId(String urId) {
		this.urId = urId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleRemark() {
		return roleRemark;
	}

	public void setRoleRemark(String roleRemark) {
		this.roleRemark = roleRemark;
	}
}
