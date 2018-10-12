/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.gaowh.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.jeespring.common.persistence.Page;
import com.jeespring.common.service.AbstractBaseService;
import com.company.project.modules.gaowh.entity.Gaowh;
import com.company.project.modules.gaowh.dao.GaowhDao;
import com.alibaba.fastjson.JSON;
import com.jeespring.common.redis.RedisUtils;
import com.jeespring.common.security.MD5Tools;

/**
 * 模块管理（上传和下载）Service
 * @author gaowenhui
 * @version 2018-09-27
 */
@Service
@Transactional(readOnly = true)
public class GaowhService extends AbstractBaseService<GaowhDao, Gaowh> {

	/**
	 * redis caches
	 */
	@Autowired
	private RedisUtils redisUtils;

	public Gaowh get(String id) {
		//获取数据库数据
		Gaowh  gaowh=super.get(id);
		return gaowh;
	}

	public Gaowh getCache(String id) {
		//获取缓存数据
		Gaowh gaowh=(Gaowh)redisUtils.get(RedisUtils.getIdKey(GaowhService.class.getName(),id));
		if( gaowh!=null) return  gaowh;
		//获取数据库数据
		gaowh=super.get(id);
		//设置缓存数据
		redisUtils.set(RedisUtils.getIdKey(GaowhService.class.getName(),id),gaowh);
		return gaowh;
	}

	public List<Gaowh> total(Gaowh gaowh) {
		//获取数据库数据
		List<Gaowh> gaowhList=super.total(gaowh);
		return gaowhList;
	}

	public List<Gaowh> totalCache(Gaowh gaowh) {
		//获取缓存数据
		String totalKey = RedisUtils.getTotalKey(GaowhService.class.getName(),JSON.toJSONString(gaowh));
		List<Gaowh> gaowhList=(List<Gaowh>)redisUtils.get(totalKey);
		if(gaowhList!=null) return gaowhList;
		//获取数据库数据
		gaowhList=super.total(gaowh);
		//设置缓存数据
		redisUtils.set(totalKey,gaowhList);
		return gaowhList;
	}

	public List<Gaowh> findList(Gaowh gaowh) {
		//获取数据库数据
		List<Gaowh> gaowhList=super.findList(gaowh);
		//设置缓存数据
		return gaowhList;
	}

	public List<Gaowh> findListCache(Gaowh gaowh) {
		//获取缓存数据
		String findListKey = RedisUtils.getFindListKey(GaowhService.class.getName(),JSON.toJSONString(gaowh));
		List<Gaowh> gaowhList=(List<Gaowh>)redisUtils.get(findListKey);
		if(gaowhList!=null) return gaowhList;
		//获取数据库数据
		gaowhList=super.findList(gaowh);
		//设置缓存数据
		redisUtils.set(findListKey,gaowhList);
		return gaowhList;
	}

	public Gaowh findListFirst(Gaowh gaowh) {;
		//获取数据库数据
		List<Gaowh> gaowhList=super.findList(gaowh);
		if(gaowhList.size()>0) gaowh=gaowhList.get(0);
		return gaowh;
	}

	public Gaowh findListFirstCache(Gaowh gaowh) {
		//获取缓存数据
		String findListFirstKey = RedisUtils.getFindListFirstKey(GaowhService.class.getName(),JSON.toJSONString(gaowh));
		Gaowh gaowhRedis=(Gaowh)redisUtils.get(findListFirstKey);
		if(gaowhRedis!=null) return gaowhRedis;
		//获取数据库数据
		List<Gaowh> gaowhList=super.findList(gaowh);
		if(gaowhList.size()>0) gaowh=gaowhList.get(0);
		else gaowh=new Gaowh();
		//设置缓存数据
		redisUtils.set(findListFirstKey,gaowh);
		return gaowh;
	}

	public Page<Gaowh> findPage(Page<Gaowh> page, Gaowh gaowh) {
		//获取数据库数据
		Page<Gaowh> pageReuslt=super.findPage(page, gaowh);
		return pageReuslt;
	}

	public Page<Gaowh> findPageCache(Page<Gaowh> page, Gaowh gaowh) {
		//获取缓存数据
		String findPageKey =  RedisUtils.getFindPageKey(GaowhService.class.getName(),JSON.toJSONString(page)+JSON.toJSONString(gaowh));
		Page<Gaowh> pageReuslt=(Page<Gaowh>)redisUtils.get(findPageKey);
		if(pageReuslt!=null) return pageReuslt;
		//获取数据库数据
		pageReuslt=super.findPage(page, gaowh);
		//设置缓存数据
		redisUtils.set(findPageKey,pageReuslt);
		return pageReuslt;
	}

	@Transactional(readOnly = false)
	public void save(Gaowh gaowh) {
		//保存数据库记录
		super.save(gaowh);
		//设置清除缓存数据
		redisUtils.remove(RedisUtils.getIdKey(GaowhService.class.getName(),gaowh.getId()));
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(GaowhService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(GaowhService.class.getName()));
	}
	
	@Transactional(readOnly = false)
	public void delete(Gaowh gaowh) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(GaowhService.class.getName(),gaowh.getId()));
		//删除数据库记录
		super.delete(gaowh);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(GaowhService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(GaowhService.class.getName()));
	}

	@Transactional(readOnly = false)
	public void deleteByLogic(Gaowh gaowh) {
		//清除记录缓存数据
		redisUtils.remove(RedisUtils.getIdKey(GaowhService.class.getName(),gaowh.getId()));
		//逻辑删除数据库记录
		super.deleteByLogic(gaowh);
		//清除列表和页面缓存数据
		redisUtils.removePattern(RedisUtils.getFindListKeyPattern(GaowhService.class.getName()));
		redisUtils.removePattern(RedisUtils.getFinPageKeyPattern(GaowhService.class.getName()));
	}

}