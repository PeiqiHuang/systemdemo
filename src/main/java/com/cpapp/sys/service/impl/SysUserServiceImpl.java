package com.cpapp.sys.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.cpapp.common.base.bean.Pagination;
import com.cpapp.common.base.service.BaseServiceImpl;
import com.cpapp.common.utils.EncryptUitls;
import com.cpapp.common.utils.LoadProperties;
import com.cpapp.common.utils.SessionUtils;
import com.cpapp.sys.entity.SysUser;
import com.cpapp.sys.service.ISysUserService;

/*******************************************************************************
 * 系统用户ServiceImpl
 * 
 * @author zengxiangtao
 * @version 2016-04-14
 ******************************************************************************/
@Service
public class SysUserServiceImpl extends BaseServiceImpl implements
		ISysUserService {

	/** 根据登录名查找登录用户 */
	@Override
	public SysUser findSysUserByLoginName(String loginName) {
		if (StringUtils.isBlank(loginName)) {
			return null;
		}
		DetachedCriteria dc = DetachedCriteria.forClass(SysUser.class);
		dc.add(Restrictions.eq("loginName", loginName.trim()));
		List<SysUser> list = baseDAO.findByCriteria(dc);
		return null == list || list.size() <= 0 ? null : list.get(0);
	}

	/** ----系统用户菜单检索---- */
	@Override
	public void searchSysUser(SysUser user, Pagination<SysUser> pagin) {
		DetachedCriteria criteria = DetachedCriteria.forClass(SysUser.class);
		if (null != user) {
			// 登录名
			if (StringUtils.isNotBlank(user.getLoginName())) {
				criteria.add(Restrictions.like("loginName",
						user.getLoginName().trim(), MatchMode.ANYWHERE)
						.ignoreCase());
			}
			// 真实姓名
			if (StringUtils.isNotBlank(user.getRealName())) {
				criteria.add(Restrictions.like("realName",
						user.getRealName().trim(), MatchMode.ANYWHERE)
						.ignoreCase());
			}
		}
		criteria.addOrder(Order.desc("createTime"));
		baseDAO.findByCriteria(criteria, pagin);
	}

	/** 根据用户ID查找用户基本信息 */
	@Override
	public SysUser findSysUserById(Long suId) {
		return null == suId ? null : baseDAO
				.findEntityById(SysUser.class, suId);
	}

	/** ---- 更新系统用户信息---- */
	@Override
	public int saveOrUpdateSysUser(SysUser su, SysUser modifier) {
		if (null == su || StringUtils.isBlank(su.getLoginName())
				|| StringUtils.isBlank(su.getRealName())) {
			return -1;
		}
		su.setModifier(SessionUtils.getLoginUserSimpleInfo(modifier));
		// 判断新增登录名是否重复
		SysUser sysUser = findSysUserByLoginName(su.getLoginName());
		if (null == su.getSuId()) {
			if (null != sysUser) {
				return -2;
			}
			// 设置默认密码
			su.setLoginPwd(EncryptUitls.MD5Digest(LoadProperties
					.getByDefaultProperties("cpAppAdmin.defaultPwd")));
			su.setCreateTime(new Date());
			su.setModifierTime(su.getCreateTime());
			baseDAO.saveEntity(su);
			return 0;
		}
		if (null != sysUser
				&& sysUser.getSuId().longValue() != su.getSuId().longValue()) {
			return -2;
		}
		if (null == sysUser) {
			sysUser = this.findSysUserById(su.getSuId());
		}
		sysUser.setLoginName(su.getLoginName());
		sysUser.setRealName(su.getRealName());
		sysUser.setStatus(su.getStatus());
		sysUser.setModifier(su.getModifier());
		sysUser.setModifierTime(new Date());
		baseDAO.updateEntity(sysUser);
		return 0;
	}

	/** ---- 重置系统用户密码---- */
	@Override
	public void updateResetUserPwd(Long suId, SysUser modifier) {
		SysUser user = findSysUserById(suId);
		if (null != user) {
			user.setLoginPwd(EncryptUitls.MD5Digest(LoadProperties
					.getByDefaultProperties("cpAppAdmin.defaultPwd")));
			user.setModifier(SessionUtils.getLoginUserSimpleInfo(modifier));
			user.setModifierTime(new Date());
			baseDAO.updateEntity(user);
		}
	}

	/** ---- 重置系统用户密码---- */
	@Override
	public int modifyUserPwd(String oldPwd, String newPwd, SysUser modifier) {
		if (null == modifier || StringUtils.isBlank(oldPwd)
				|| StringUtils.isBlank(newPwd)) {
			return -1;
		}
		SysUser user = findSysUserById(modifier.getSuId());
		if (null == user) {
			return -1;
		}
		String oldPwd_md5 = EncryptUitls.MD5Digest(oldPwd.trim());
		if (!StringUtils.equals(oldPwd_md5, user.getLoginPwd())) {
			return -2;
		}
		user.setLoginPwd(EncryptUitls.MD5Digest(newPwd.trim()));
		user.setModifier(SessionUtils.getLoginUserSimpleInfo(modifier));
		user.setModifierTime(new Date());
		baseDAO.updateEntity(user);
		return 0;
	}
}
