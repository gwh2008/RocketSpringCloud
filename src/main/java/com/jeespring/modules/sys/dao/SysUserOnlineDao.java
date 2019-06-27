/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.sys.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.sys.entity.SysUserOnline;

/**
 * 在线用户记录DAO接口
 * @author gaowh
 * @version 2018-08-16
 */
@Mapper
public interface SysUserOnlineDao extends InterfaceBaseDao<SysUserOnline> {
	
}