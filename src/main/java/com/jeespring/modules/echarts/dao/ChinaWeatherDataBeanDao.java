
package com.jeespring.modules.echarts.dao;

import com.jeespring.common.persistence.InterfaceBaseDao;
import com.jeespring.modules.echarts.entity.ChinaWeatherDataBean;
import org.apache.ibatis.annotations.Mapper;

/**
 * 城市气温DAO接口
 * @author gaowh
 * @version 2016-06-02
 */
@Mapper
public interface ChinaWeatherDataBeanDao extends InterfaceBaseDao<ChinaWeatherDataBean> {

	
}