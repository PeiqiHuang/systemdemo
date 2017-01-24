package com.cpapp.user.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tb_cpapp_user")
public class User {

	// 会员编号
	@Id
	@Column(name = "userId")
	private String userId;

	// 会员名称
	@Column(name = "userName")
	private String userName;

	// 会员密码
	@Column(name = "userPwd")
	private String userPwd;

	// 会员手机号
	@Column(name = "mobilePhone")
	private String mobilePhone;

	// 会员状态 1正常 0禁用
	@Column(name = "userStatus")
	private Integer userStatus;

	// 创建时间
	@Column(name = "createTime")
	private Date createTime;

	// 用户头像URL
	@Transient
	private String headPhotourl;

	// 用户昵称
	@Transient
	private String nickName;

	// 性别
	@Transient
	private String SEX;

	// 所在行政区划
	@Transient
	private String districtCode;

	// 邮箱
	@Transient
	private String eMail;

	// 更新时间
	@Transient
	private Date updateTime;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Integer getUserStatus() {
		return userStatus;
	}

	public void setUserStatus(Integer userStatus) {
		this.userStatus = userStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getHeadPhotourl() {
		return headPhotourl;
	}

	public void setHeadPhotourl(String headPhotourl) {
		this.headPhotourl = headPhotourl;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSEX() {
		return SEX;
	}

	public void setSEX(String sEX) {
		SEX = sEX;
	}

	public String getDistrictCode() {
		return districtCode;
	}

	public void setDistrictCode(String districtCode) {
		this.districtCode = districtCode;
	}

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
