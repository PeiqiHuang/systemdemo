package com.cpapp.auth.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/*******************************************************************************
 * 系统菜单 (bk_auth_menu)
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
@Entity
@Table(name = "admin_menu")
/*@SequenceGenerator(name = "menuSeq", sequenceName = "seq_cpapp_menu", allocationSize = 1)*/
public class Menu {

	@Id
	@GeneratedValue/*(strategy = GenerationType.SEQUENCE, generator = "menuSeq")*/
	@Column(name = "menuId", unique = true, nullable = false)
	private Long menuId;
	// 菜单名称
	@Column(name = "menuName")
	private String menuName;
	// 链接地址
	@Column(name = "linkAddress")
	private String linkAddress;
	// 菜单类型[1:系统级;2:菜单级;3:按纽级]
	@Column(name = "menuType")
	private Integer menuType;
	// 菜单Icon
	@Column(name = "menuIcon")
	private String menuIcon;
	// 排序
	@Column(name = "sortNum")
	private Integer sortNum;
	// 父ID
	@Column(name = "parentId")
	private Long parentId;
	// 创建时间
	@Column(name = "createTime")
	private Date createTime;

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

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

	public Integer getMenuType() {
		return menuType;
	}

	public void setMenuType(Integer menuType) {
		this.menuType = menuType;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public Integer getSortNum() {
		return sortNum;
	}

	public void setSortNum(Integer sortNum) {
		this.sortNum = sortNum;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
