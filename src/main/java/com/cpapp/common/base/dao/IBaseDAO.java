package com.cpapp.common.base.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.base.hibernate.SQLQueryCriteria;

/*******************************************************************************
 * DAO基类
 * 
 * @author zengxiangtao
 * @version 2016-07-25
 ******************************************************************************/
public interface IBaseDAO {

	/*--提前hibernate当前缓存的数据--*/
	public void HibernateTemplateFlush();

	/*--删除hibernate当前会话中缓存的对象--*/
	public void HibernateTemplateClear();

	/*--保存对象--*/
	public Serializable saveEntity(Object entity);

	/*--更新对象基本信息--*/
	public <T> T updateEntity(T entity);

	/*--批量删除--*/
	public <T> int deleteByHQL(Class<T> entityClass, String propertyName,
			Object[] ids);

	/*--主键查找--*/
	public <T> T findEntityById(Class<T> entityClass, Serializable id);

	/*--DetachedCriteria检索--*/
	public <T> List<T> findByCriteria(DetachedCriteria criteria);

	/*--DetachedCriteria检索[限制结果集]--*/
	public <T> List<T> findByCriteria(DetachedCriteria criteria,
			int firstResult, int maxResults);

	/*--DetachedCriteria检索[分页查询]--*/
	public <T> void findByCriteria(DetachedCriteria criteria,
			Pagination<T> pagin);

	/*--SQL自定义检索--*/
	public <T> void findBySQLQueryCriteria(SQLQueryCriteria<T> sqc,
			Pagination<T> pagin);

	// 批处理
	public void excuteBatchData(String sql, List<Object[]> param);
}
