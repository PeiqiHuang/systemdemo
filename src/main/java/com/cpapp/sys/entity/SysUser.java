package com.cpapp.sys.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*******************************************************************************
 * 系统用户(bk_sys_sysuser)
 * 
 * @version 2016-10-25
 ******************************************************************************/
@Entity
@Table(name = "admin_sysuser")
/*@SequenceGenerator(name = "sysUserSeq", sequenceName = "seq_cpapp_sysUser", allocationSize = 1)*/
public class SysUser {
	@Id
	@GeneratedValue
	@Column(name = "suId", unique = true, nullable = false)
	private Long suId;
	// 登录名
	@Column(name = "loginName")
	private String loginName;
	// 真实姓名
	@Column(name = "realName")
	private String realName;
	// 登录密码
	@Column(name = "loginPwd")
	private String loginPwd;
	// 状态
	@Column(name = "status")
	private Integer status;
	// 修改者信息
	@Column(name = "modifier")
	private String modifier;
	// 最后一次修改时间
	@Column(name = "modifierTime")
	private Date modifierTime;
	// 创建时间
	@Column(name = "createTime")
	private Date createTime;

	public Long getSuId() {
		return suId;
	}

	public void setSuId(Long suId) {
		this.suId = suId;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getLoginPwd() {
		return loginPwd;
	}

	public void setLoginPwd(String loginPwd) {
		this.loginPwd = loginPwd;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getModifierTime() {
		return modifierTime;
	}

	public void setModifierTime(Date modifierTime) {
		this.modifierTime = modifierTime;
	}

	@Override
	public String toString() {
		return "SysUser [suId=" + suId + ", loginName=" + loginName
				+ ", realName=" + realName + ", loginPwd=" + loginPwd
				+ ", status=" + status + ", modifier=" + modifier
				+ ", modifierTime=" + modifierTime + ", createTime="
				+ createTime + "]";
	}
	
}
