/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.ask.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.company.project.modules.ask.entity.Ask;
import com.company.project.modules.ask.dao.AskDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;

/**
 * 问卷调查描述Service
 * @author gaowenhui
 * @version 2018-10-12
 */
@Service
@Transactional(readOnly = true)
public class AskService extends AbstractBaseService<AskDao, Ask> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	public Ask get(String id) {
		//获取数据库数据
		Ask  ask=super.get(id);
		return ask;
	}

	public Ask getCache(String id) {
		//获取缓存数据
		Ask ask=(Ask)redisUtils.get(RedisUtils.getIdKey(AskService.class.getName(),id));
		if( ask!=null) return  ask;
		//获取数据库数据
		ask=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(AskService.class.getName(),id),ask);
		return ask;
	}

	public List<Ask> total(Ask ask) {
		//获取数据库数据
		List<Ask> askList=super.total(ask);
		return askList;
	}

	public List<Ask> totalCache(Ask ask) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(AskService.class.getName(),JSON.toJSONString(ask));
		List<Ask> askList=(List<Ask>)redisUtils.get(totalKey);
		if(askList!=null) return askList;
		//获取数据库数据
		askList=super.total(ask);
		//设置缓存数据
		redisUtils.set(totalKey,askList);
		return askList;
	}

	public List<Ask> findList(Ask ask) {
		//获取数据库数据
		List<Ask> askList=super.findList(ask);
		//设置缓存数据
		return askList;
	}

	public List<Ask> findListCache(Ask ask) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(AskService.class.getName(),JSON.toJSONString(ask));
		List<Ask> askList=(List<Ask>)redisUtils.get(findListKey);
		if(askList!=null) return askList;
		//获取数据库数据
		askList=super.findList(ask);
		//设置缓存数据
		redisUtils.set(findListKey,askList);
		return askList;
	}

	public Ask findListFirst(Ask ask) {;
		//获取数据库数据
		List<Ask> askList=super.findList(ask);
		if(askList.size()>0) ask=askList.get(0);
		return ask;
	}

	public Ask findListFirstCache(Ask ask) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(AskService.class.getName(),JSON.toJSONString(ask));
		Ask askRedis=(Ask)redisUtils.get(findListFirstKey);
		if(askRedis!=null) return askRedis;
		//获取数据库数据
		List<Ask> askList=super.findList(ask);
		if(askList.size()>0) ask=askList.get(0);
		else ask=new Ask();
		//设置缓存数据
		redisUtils.set(findListFirstKey,ask);
		return ask;
	}

	public Page<Ask> findPage(Page<Ask> page, Ask ask) {
		//获取数据库数据
		Page<Ask> pageReuslt=super.findPage(page, ask);
		return pageReuslt;
	}

	public Page<Ask> findPageCache(Page<Ask> page, Ask ask) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(AskService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(ask));
		Page<Ask> pageReuslt=(Page<Ask>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) return pageReuslt;
		//获取数据库数据
		pageReuslt=super.findPage(page, ask);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Transactional(readOnly = false)
	public void save(Ask ask) {
		//保存数据库记录
		super.save(ask);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(AskService.class.getName(),ask.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(AskService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(AskService.class.getName()));
	}
	
	@Transactional(readOnly = false)
	public void delete(Ask ask) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(AskService.class.getName(),ask.getId()));
		//删除数据库记录
		super.delete(ask);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(AskService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(AskService.class.getName()));
	}

	@Transactional(readOnly = false)
	public void deleteByLogic(Ask ask) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(AskService.class.getName(),ask.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(ask);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(AskService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(AskService.class.getName()));
	}

}