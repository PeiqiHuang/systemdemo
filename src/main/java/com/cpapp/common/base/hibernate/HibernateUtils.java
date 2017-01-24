package com.cpapp.common.base.hibernate;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.Transformers;

/*******************************************************************************
 * Hibernate工具类
 * 
 * @author zengxiangtao
 * @version 2016-07-31
 ******************************************************************************/
public class HibernateUtils {

	/** 利用Java反射机制将DetachedCriteria里removeOrder属性* */
	@SuppressWarnings("rawtypes")
	public static Criteria removeCriteriaOrderEntity(DetachedCriteria dc,
			Session session) {
		Criteria criteria = dc.getExecutableCriteria(session);
		// 利用java反射机制,将order属性置空
		Field field = null;
		try {
			field = CriteriaImpl.class.getDeclaredField("orderEntries");
			field.setAccessible(true);
			field.set(criteria, new ArrayList());
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return criteria;
	}

	/** 构建部分查询DetachedCriteria(单表)* */
	@SuppressWarnings("rawtypes")
	public static DetachedCriteria createColumnCriteria(String[] columnName,
			Class pojoClass) {
		DetachedCriteria criteria = DetachedCriteria.forClass(pojoClass);
		ProjectionList projectList = Projections.projectionList();
		if (null != columnName && columnName.length > 0) {
			for (String colum : columnName) {
				projectList.add(Projections.property(colum).as(colum));
			}
		}
		criteria.setProjection(projectList);
		criteria.setResultTransformer(Transformers.aliasToBean(pojoClass));
		return criteria;
	}

	/** 构建where查询子句* */
	public static String createConditionSQL(List<SimpleExpression> seList) {
		StringBuffer conditionSQL = new StringBuffer();
		if (null != seList && seList.size() > 0) {
			// 条件表达式
			Field op = HibernateUtils.getSimpleExpressionOpField("op");
			// 是否忽略大小写
			Field ignoreCase = HibernateUtils
					.getSimpleExpressionOpField("ignoreCase");
			for (int i = 0; i < seList.size(); i++) {
				SimpleExpression se = seList.get(i);
				conditionSQL.append(i == 0 ? " where  " : " and ");
				try {
					conditionSQL
							.append(ignoreCase.get(se).equals(true) ? "lower("
									+ se.getPropertyName() + ")" : se
									.getPropertyName());
					conditionSQL.append(op.get(se));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				conditionSQL.append(" ? ");
			}
		}
		return conditionSQL.toString();
	}

	/** 构建orderby查询子句* */
	public static String createOrderByStr(List<Order> orders) {
		StringBuffer orderSQL = new StringBuffer();
		if (null != orders && orders.size() > 0) {
			orderSQL.append(" order by ");
			for (Order o : orders) {
				orderSQL.append(o.toString()).append(",");
			}
			return orderSQL.substring(0, orderSQL.length() - 1);
		}
		return "";
	}

	/** 利用Java反射机制从SimpleExpression表达式的字段* */
	public static Field getSimpleExpressionOpField(String fieldName) {
		Field field = null;
		try {
			// ignoreCase
			field = SimpleExpression.class.getDeclaredField(fieldName);
			field.setAccessible(true);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return field;
	}
}
