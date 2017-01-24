package com.cpapp.crud.service;

import com.cpapp.common.base.bean.QueryParamBean;

public interface ICommonCRUDService {
	
	public <T> void searchSysRole(Class c, String pattern, QueryParamBean<T> pagin);
}
