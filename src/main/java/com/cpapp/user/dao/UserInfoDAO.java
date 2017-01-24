package com.cpapp.user.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;


import com.cpapp.user.entity.User;
import com.cpapp.common.base.dao.BaseDAOImpl;
import com.cpapp.common.base.hibernate.SimpleColumnToBean;

/*******************************************************************************
 * 用户信息DAO
 ******************************************************************************/
@Repository(value = "userInfoDAO")
public class UserInfoDAO extends BaseDAOImpl {



	/*---- 用户信息_属性信息---- */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public User findUserDetail( String userId) {
		String sql = "select t.userid as userId ,t.username as userName,t.userpwd as userPwd , t.mobilephone as mobilePhone, t.userstatus as userStatus , t.createtime as createTime,r.headphotourl as headPhotourl,r.nickname as nickName,r.sex as SEX, r.districtcode as districtCode,r.email as eMail,r.updatetime as updateTime from tb_cpapp_user t left join tb_cpapp_userattr r on t.userid = r.userid where t.userid = ? ";
		SQLQuery query = currentSession().createSQLQuery(sql);
		query.setString(0, userId);
		query.setResultTransformer(new SimpleColumnToBean(User.class));
		List<User> list = query.list();
		return null == list || list.size() < 1 ? null : list.get(0);
	}



}
