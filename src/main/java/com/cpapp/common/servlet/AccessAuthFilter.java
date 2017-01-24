package com.cpapp.common.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cpapp.auth.service.IUserAuthService;
import com.cpapp.common.constants.SysConfig;
import com.cpapp.common.utils.LoadProperties;
import com.cpapp.common.utils.ServiceFacade;
import com.cpapp.common.utils.SessionUtils;
import com.cpapp.sys.entity.SysUser;

/*******************************************************************************
 * 后台访问权限拦截器
 * 
 * @author zengxiangtao
 ******************************************************************************/
public class AccessAuthFilter implements Filter {

	/** Filter初始化 */
	@Override
	public void init(FilterConfig config) throws ServletException {

	}

	/** 过滤逻辑设计 */
	@Override
	public void doFilter(ServletRequest srequest, ServletResponse sresponse,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) srequest;
		HttpServletResponse response = (HttpServletResponse) sresponse;
		/*------项目路径----------*/
		String base = request.getContextPath();
		request.setAttribute("base", base);
		/*------资源路径----------*/
		String resource = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ LoadProperties.getByDefaultProperties("my_ImagePath");
		request.setAttribute("resource", resource);
		/*-----不缓存--------*/
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		/*------访问路径----------*/
		String requestPath = request.getRequestURI().replace(
				request.getContextPath(), "");
		/*--非受限资源-----*/
		if (requestPath.indexOf("admin") == -1) {
			chain.doFilter(srequest, sresponse);
			return;
		}
		/*--1.免用户判断-----*/
		if (requestPath.endsWith("admin/sys/login.do")
				|| requestPath.endsWith("admin/sys/logout.do")) {
			chain.doFilter(srequest, sresponse);
			return;
		}
		/*--2.登录判断-----*/
		HttpSession hsession = request.getSession(false);
		SysUser loginUser = SessionUtils.getSystemLoginUser(hsession);
		if (null == loginUser) {
			PrintWriter out = response.getWriter();
			out.println("<script lanugage='javascript'>alert('\\u8bf7\\u5148\\u767b\\u5f55!'); top.window.location.href='"
					+ request.getContextPath() + "/index.jsp';</script>");
			out.close();
			return;
		}
		/*--3.合法用户(超级管理员),免检权限-----*/
		if (loginUser.getSuId().longValue() == SysConfig.SYS_ADMIN_USERID
				|| requestPath.endsWith("admin/sys/modifyLoginPwd.do")
				|| requestPath.endsWith("admin/sys/userSubMenuList.do")
				|| requestPath.endsWith("admin/sys/index.do")
				|| requestPath.indexOf("/admin/upload/") != -1) {
			chain.doFilter(srequest, sresponse);
			return;
		}
		/*--4.访问权限校验-----*/
		boolean isAccessible = ServiceFacade
				.getBean(IUserAuthService.class).userRightValidate(
						loginUser.getSuId(), requestPath);
		if (!isAccessible) {
			PrintWriter out = response.getWriter();
			out.println("<script lanugage='javascript'>window.location.href='"
					+ request.getContextPath()
					+ "/resource/admin/illegalPermission.jsp?reqPath="
					+ requestPath + "';</script>");
			out.close();
			return;
		}
		/*------执行----------*/
		chain.doFilter(srequest, sresponse);
	}

	@Override
	public void destroy() {

	}

}
