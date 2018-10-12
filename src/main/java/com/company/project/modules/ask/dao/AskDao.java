/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.ask.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.company.project.modules.ask.entity.Ask;

/**
 * 问卷调查描述DAO接口
 * @author gaowenhui
 * @version 2018-10-12
 */
@Mapper
public interface AskDao extends InterfaceBaseDao<Ask> {
	
}