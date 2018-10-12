/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.jeespring.modules.sys.dao;

import java.util.List;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.jeespring.modules.sys.entity.Dict;

/**
 * 字典DAO接口
 * @author gaowh
 * @version 2014-05-16
 */
@Mapper
public interface DictDao extends InterfaceBaseDao<Dict> {

	List<String> findTypeList(Dict dict);
	
}
