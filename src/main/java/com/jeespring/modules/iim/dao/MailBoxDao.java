/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.iim.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.iim.entity.MailBox;

/**
 * 发件箱DAO接口
 * * * * @author gaowh
 * @version 2015-11-15
 */
@Mapper
public interface MailBoxDao extends InterfaceBaseDao<MailBox> {
	
	int getCount(MailBox entity);
	
}