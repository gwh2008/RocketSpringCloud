/**
 * Copyright &copy; 2012-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">jeespring</a> All rights reserved.
 */
package com.jeespring.common.filter;

import com.jeespring.common.utils.SpringContextHolder;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

/**
 * 页面高速缓存过滤器
 * @author gaowh
 * @version 2013-8-5
 */
public class PageCachingFilter extends SimplePageCachingFilter {

	private CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
	
	@Override
	protected CacheManager getCacheManager() {
		this.cacheName = "pageCachingFilter";
		return cacheManager;
	}
	
}
