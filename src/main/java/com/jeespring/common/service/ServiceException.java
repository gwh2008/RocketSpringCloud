/**
 * Copyright &copy; 2012-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">jeespring</a> All rights reserved.
 */
package com.jeespring.common.service;

/**
 * Service层公用的Exception, 从由Spring管理事务的函数中抛出时会触发事务回滚.
 * @author gaowh
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ServiceException(String message) {
		super(message);
	}

	public ServiceException(Throwable cause) {
		super(cause);
	}

}
