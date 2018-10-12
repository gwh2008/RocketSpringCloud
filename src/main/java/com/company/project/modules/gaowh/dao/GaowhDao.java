/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.gaowh.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import org.apache.ibatis.annotations.Mapper;
import com.company.project.modules.gaowh.entity.Gaowh;

/**
 * 模块管理（上传和下载）DAO接口
 * @author gaowenhui
 * @version 2018-09-27
 */
@Mapper
public interface GaowhDao extends InterfaceBaseDao<Gaowh> {
	
}