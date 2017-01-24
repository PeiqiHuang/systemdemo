package com.cpapp.common.base.service;

import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cpapp.common.base.dao.IBaseDAO;


/*******************************************************************************
 * ServiceImpl基类
 * 
 * @author zengxiangtao
 * @version 2016-07-25
 ******************************************************************************/
public abstract  class BaseServiceImpl {

	@Resource(name = "baseDAOImpl")
	protected IBaseDAO baseDAO;
	
	protected String getWebappsPath() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		String ctxPath = request.getServletContext().getRealPath("");
		if (ctxPath.endsWith(File.separator)) {
			ctxPath = ctxPath.substring(0, ctxPath.length() - 1);
		}
		String webName = request.getServletContext().getContextPath();
		ctxPath = ctxPath.replace(File.separator + webName.substring(1), "");
		return ctxPath;
	}
	
	protected String getBaseUrl() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest(); 
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
	}
}
