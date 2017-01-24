package com.cpapp.common.base.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.jdbc.Work;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.base.hibernate.HibernateUtils;
import com.cpapp.common.base.hibernate.SQLQueryCriteria;
import com.cpapp.common.base.hibernate.SimpleColumnToBean;

/*******************************************************************************
 * DAO基类
 * 
 * @author zengxiangtao
 * @version 2014-02-21
 ******************************************************************************/
@SuppressWarnings({ "rawtypes", "unchecked" })
@Repository(value = "baseDAOImpl")
public class BaseDAOImpl extends HibernateDaoSupport implements IBaseDAO {

	/** 提交当前hibernate 缓存 */
	@Override
	public void HibernateTemplateFlush() {
		getHibernateTemplate().flush();
	}

	/** 清空当前Session 缓存 */
	@Override
	public void HibernateTemplateClear() {
		getHibernateTemplate().clear();
	}

	/** Hibernate保存对象 **/
	@Override
	public Serializable saveEntity(Object entity) {
		return getHibernateTemplate().save(entity);
	}

	/** 更新一个对象 **/
	@Override
	public <T> T updateEntity(T entity) {
		return getHibernateTemplate().merge(entity);
	}

	/** 批量删除 **/
	@Override
	public <T> int deleteByHQL(Class<T> entityClass, String propertyName,
			Object[] ids) {
		String queryString = "delete from " + entityClass.getSimpleName()
				+ " where " + propertyName + " in ( :ids ) ";
		return currentSession().createQuery(queryString)
				.setParameterList("ids", ids).executeUpdate();
	}

	/** 根据ID查找对象 **/
	@Override
	public <T> T findEntityById(Class<T> entityClass, Serializable id) {
		return getHibernateTemplate().get(entityClass, id);
	}

	/**
	 * Hibernate DetachedCriteria查询
	 * 
	 * @param <T>
	 * @param DetachedCriteria
	 * @return List<T>
	 * **/
	@Override
	public <T> List<T> findByCriteria(DetachedCriteria criteria) {
		return (List<T>) getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * Hibernate DetachedCriteria查询
	 * 
	 * @param <T>
	 * @param DetachedCriteria
	 * @param firstResult
	 * @param maxResults
	 * @return List<T>
	 * **/
	@Override
	public <T> List<T> findByCriteria(DetachedCriteria criteria,
			int firstResult, int maxResults) {
		return (List<T>) getHibernateTemplate().findByCriteria(criteria,
				firstResult, maxResults);
	}

	/**
	 * Hibernate DetachedCriteria查询
	 * 
	 * @param pagin
	 * @param DetachedCriteria
	 * **/
	@Override
	public <T> void findByCriteria(DetachedCriteria detachedCriteria,
			Pagination<T> pagin) {
		// 分页查询结果集
		List<T> result = (List<T>) getHibernateTemplate().findByCriteria(
				detachedCriteria, pagin.getFromRowNum(), pagin.getPageSize());
		pagin.setResultLists(result);
		// 查询总行数
		detachedCriteria.setProjection(null);
		detachedCriteria.setProjection(Projections.projectionList().add(
				Projections.rowCount()));
		HibernateUtils.removeCriteriaOrderEntity(detachedCriteria,
				getSessionFactory().getCurrentSession());
		List<Long> list = (List<Long>) getHibernateTemplate().findByCriteria(
				detachedCriteria, 0, 1);
		pagin.setTotalRows(null == list || list.size() == 0 ? 0 : list.get(0)
				.intValue());
	}

	/** --SQL自定义检索-- */
	@Override
	public <T> void findBySQLQueryCriteria(SQLQueryCriteria<T> sqc,
			Pagination<T> pagin) {
		if (null == sqc) {
			return;
		}
		SQLQuery query = currentSession().createSQLQuery(sqc.generateDataSQL());
		SQLQuery count = currentSession()
				.createSQLQuery(sqc.generateCountSQL());
		// 参数组装
		if (null != sqc.getConditions()) {
			int size = sqc.getConditions().size();
			for (int i = 0; i < size; i++) {
				query.setParameter(i, sqc.getConditions().get(i).getValue());
				count.setParameter(i, sqc.getConditions().get(i).getValue());
			}
		}
		query.setFirstResult(pagin.getFromRowNum());
		query.setMaxResults(pagin.getEndRowNum());
		query.setResultTransformer(null == sqc.getMappedClass() ? Transformers.ALIAS_TO_ENTITY_MAP
				: new SimpleColumnToBean(sqc.getMappedClass()));
		pagin.setResultLists(query.list());
		// 查询总行数
		count.addScalar("count", IntegerType.INSTANCE);
		Integer totalRows = (Integer) count.uniqueResult();
		pagin.setTotalRows(totalRows);
	}

	/** 批处理 **/
	@Override
	public void excuteBatchData(final String sql, final List<Object[]> param) {
		currentSession().doWork(new Work() {
			@Override
			public void execute(Connection conn) throws SQLException {
				PreparedStatement prest = null;
				try {
					prest = conn.prepareStatement(sql,
							ResultSet.TYPE_SCROLL_SENSITIVE,
							ResultSet.CONCUR_READ_ONLY);
					for (int x = 0; x < param.size(); x++) {
						Object[] arr = param.get(x);
						int k = 1;
						for (Object o : arr) {
							prest.setObject(k, o);
							k++;
						}
						prest.addBatch();
					}
					prest.executeBatch();
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					try {
						if (null != prest) {
							prest.close();
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}

			}
		});
	}
}
