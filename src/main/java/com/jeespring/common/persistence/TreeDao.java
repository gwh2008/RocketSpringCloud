/**
 * Copyright &copy; 2012-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">jeespring</a> All rights reserved.
 */
package com.jeespring.common.persistence;

import java.util.List;

/**
 * DAO支持类实现
 * @author gaowh
 * @version 2014-05-16
 * @param <T>
 */
public interface TreeDao<T extends TreeEntity<T>> extends InterfaceBaseDao<T> {

	/**
	 * 找到所有子节点
	 * @param entity
	 * @return
	 */
	  List<T> findByParentIdsLike(T entity);

	/**
	 * 更新所有父节点字段
	 * @param entity
	 * @return
	 */
	  int updateParentIds(T entity);
	
}