/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.test.dao.one;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.test.entity.one.FormLeave;

/**
 * 员工请假DAO接口
 * @author gaowh
 * @version 2018-08-03
 */
@Mapper
public interface FormLeaveDao extends InterfaceBaseDao<FormLeave> {
	
}