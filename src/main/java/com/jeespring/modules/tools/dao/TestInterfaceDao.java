/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.tools.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.tools.entity.TestInterface;

/**
 * 接口DAO接口
 * @author gaowh
 * @version 2016-01-07
 */
@Mapper
public interface TestInterfaceDao extends InterfaceBaseDao<TestInterface> {
	
}