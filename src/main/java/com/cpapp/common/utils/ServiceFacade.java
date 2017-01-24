package com.cpapp.common.utils;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.ContextLoader;

/*******************************************************************************
 * 系统服务门面类
 * 
 * @author zengxiangtao
 * @version 2016-04-23
 ******************************************************************************/
public class ServiceFacade {

	/**
	 * 从spring容器里获bean
	 * 
	 * @author zengxiangtao
	 * @param <T>
	 * */
	public static <T> T getBean(String beanName, Class<T> requiredType) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(
				beanName, requiredType);
	}

	/**
	 * 从spring容器里获bean
	 * 
	 * @author zengxiangtao
	 * @param <T>
	 * */
	public static <T> T getBean(Class<T> requiredType) {
		return ContextLoader.getCurrentWebApplicationContext().getBean(
				requiredType);
	}

	/**
	 * Spring 异步执行器
	 * 
	 * @author zengxiangtao
	 * @param <T>
	 * */
	public static ThreadPoolTaskExecutor getTaskExecutor() {
		return getBean(ThreadPoolTaskExecutor.class);
	}
}
