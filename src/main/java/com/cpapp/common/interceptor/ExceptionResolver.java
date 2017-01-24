package com.cpapp.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;

/*******************************************************************************
 * 异常日志处理类
 * 
 * @author zengxiangtao
 * @version 2015-03-11
 ******************************************************************************/
@Component
public class ExceptionResolver implements HandlerExceptionResolver {
	private Logger log = Logger.getLogger(ExceptionResolver.class);

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception e) {
		ModelAndView mav = new ModelAndView("/admin/exception");
		log.error(getStackMsg(request.getRequestURI()+",params:"+JSON.toJSONString(request.getParameterMap()), e));
		System.out.println(getStackMsg(request.getRequestURI()+",params:"+JSON.toJSONString(request.getParameterMap()), e));
		mav.addObject("exception", e);
		return mav;
	}

	/** 打印异常信息 */
	private static String getStackMsg(String reqData, Throwable e) {
		StringBuffer sb = new StringBuffer();
		sb.append(reqData + "\r\n");
		sb.append(e.getMessage() + "\r\n");
		StackTraceElement[] stackArray = e.getStackTrace();
		for (int i = 0; i < stackArray.length; i++) {
			StackTraceElement element = stackArray[i];
			sb.append(element.toString() + "\r\n");
		}
		return sb.toString();
	}
}
