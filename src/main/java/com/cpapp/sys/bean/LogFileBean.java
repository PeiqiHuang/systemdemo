package com.cpapp.sys.bean;

import java.util.ArrayList;
import java.util.List;
/*******************************************************************************
 * 日志文件bean
 * 
 * @author zengxiangtao
 * @version 2016-07-10
 ******************************************************************************/
public class LogFileBean {

	private Long id;
	private String fileName;
	private String downloadPath;
	private boolean folder;
	private Long fileSize;
	private String dateStr;
	private List<LogFileBean> children;
	private String state;
	private String iconCls;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getFolder() {
		return folder;
	}

	public void setFolder(boolean folder) {
		this.folder = folder;
	}

	public List<LogFileBean> getChildren() {
		if(null == children){
			children = new ArrayList<LogFileBean>();
		}
		return children;
	}

	public void setChildren(List<LogFileBean> children) {
		this.children = children;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDownloadPath() {
		return downloadPath;
	}

	public void setDownloadPath(String downloadPath) {
		this.downloadPath = downloadPath;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	
}
