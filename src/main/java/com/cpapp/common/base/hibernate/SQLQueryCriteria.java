package com.cpapp.common.base.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.SimpleExpression;

/*******************************************************************************
 * SQL Query DTO
 * 
 * @author zengxiangtao
 * @version 2014-02-21
 ******************************************************************************/
public class SQLQueryCriteria<T> {
	// 查询的SQL
	private String tableSqlStr;
	// 统计总行数的SQL
	private String countSqlStr;
	// 条件
	private List<SimpleExpression> conditions = new ArrayList<SimpleExpression>();
	// 排序对象
	private List<Order> orderLists = new ArrayList<Order>();
	// 查询结果集映射对象(没有指定映射对象是使用map)
	private Class<T> mappedClass;

	private String conditionSQL;

	/** 不提供无参构造,避免SQL参数丢失 */
	@SuppressWarnings("unused")
	private SQLQueryCriteria() {
	}

	public SQLQueryCriteria(String tableSqStr) {
		this.tableSqlStr = tableSqStr;
	}

	public SQLQueryCriteria(String tableSqStr, Class<T> mappedClass) {
		this.tableSqlStr = tableSqStr;
		this.mappedClass = mappedClass;
	}

	public void addOrderBy(Order o) {
		orderLists.add(o);
	}

	public void addSearchConditions(SimpleExpression se) {
		conditions.add(se);
	}

	public void setCountSqlStr(String countSqlStr) {
		this.countSqlStr = countSqlStr;
	}

	public List<SimpleExpression> getConditions() {
		return conditions;
	}

	public Class<T> getMappedClass() {
		return mappedClass;
	}

	public void setMappedClass(Class<T> mappedClass) {
		this.mappedClass = mappedClass;
	}

	public void setConditions(List<SimpleExpression> conditions) {
		this.conditions = conditions;
	}

	private String getConditionSQL() {
		if (StringUtils.isBlank(conditionSQL)) {
			this.conditionSQL = HibernateUtils.createConditionSQL(conditions);
		}
		return conditionSQL;
	}

	/** 生成查询的SQL */
	public String generateDataSQL() {
		StringBuffer sql = new StringBuffer(tableSqlStr);
		sql.append(getConditionSQL());
		sql.append(HibernateUtils.createOrderByStr(orderLists));
		return sql.toString();
	}

	/** 生成查询的SQL */
	public String generateCountSQL() {
		StringBuffer sql = new StringBuffer();
		if (StringUtils.isNotBlank(countSqlStr)) {
			sql.append(countSqlStr);
		} else {
			sql.append("select count(*) as count from (").append(tableSqlStr);
		}
		sql.append(getConditionSQL());
		sql.append(StringUtils.isNotBlank(countSqlStr) ? "" : ")");
		return sql.toString();
	}

}
