package com.cpapp.common.base.hibernate;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.transform.ResultTransformer;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.PropertyValue;

/*******************************************************************************
 * 结果封装类
 * 
 * @author zengxiangtao
 * @version 2013-11-18
 * @param <T>
 ******************************************************************************/
public class SimpleColumnToBean<T> implements ResultTransformer {

	private static final long serialVersionUID = 1L;
	private Class<T> resultClass;
	private Map<String, String> propertyMap = new HashMap<String, String>();

	public SimpleColumnToBean(Class<T> resultClass) {
		this.resultClass = resultClass;
		// 取得POJO所有属性名
		Field[] fields = resultClass.getDeclaredFields();
		if (fields == null || fields.length == 0) {
			throw new RuntimeException("实体" + resultClass.getName() + "不含任何属性");
		}
		for (Field field : fields) {
			propertyMap.put(field.getName().toUpperCase(), field.getName());
		}
		fields = resultClass.getSuperclass().getDeclaredFields();
		for (Field field : fields) {
			propertyMap.put(field.getName().toUpperCase(), field.getName());
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List transformList(List arg0) {
		return arg0;
	}

	@Override
	public Object transformTuple(Object[] tuple, String[] aliases) {
		Object result = null;
		try {
			result = resultClass.newInstance();
			BeanWrapper bw = new BeanWrapperImpl(result);
			for (int i = 0; i < aliases.length; i++) {
				String p = aliases[i].toUpperCase();
				System.out.println("p = " + p);
				if (propertyMap.containsKey(p)) {
					bw.setPropertyValue(new PropertyValue(propertyMap.get(p),
							tuple[i]));
				}
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return result;
	}
}
