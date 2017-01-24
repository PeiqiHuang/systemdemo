package com.cpapp.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/*******************************************************************************
 * 系统角色 (bk_auth_Role)
 * 
 * @version 2016-10-25
 ******************************************************************************/
@Entity
@Table(name = "admin_role")
public class AuthRole {

	@Id
	@Column(name = "roleId")
	private String roleId;
	@Column(name = "roleName")
	private String roleName;
	@Column(name = "remark")
	private String remark;
	@Column(name = "createTime")
	private Date createTime;

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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
