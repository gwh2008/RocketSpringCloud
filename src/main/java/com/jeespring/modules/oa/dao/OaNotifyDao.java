/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.oa.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.oa.entity.OaNotify;

/**
 * 通知通告DAO接口
 * @author gaowh
 * @version 2014-05-16
 */
@Mapper
public interface OaNotifyDao extends InterfaceBaseDao<OaNotify> {
	
	/**
	 * 获取通知数目
	 * @param oaNotify
	 * @return
	 */
    Long findCount(OaNotify oaNotify);
	
}