/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.gaowh.entity;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 模块管理（上传和下载）Entity
 * @author gaowenhui
 * @version 2018-11-21
 */
public class Gaowh extends AbstractBaseEntity<Gaowh> {
	
	private static final long serialVersionUID = 1L;
	private String moduleName;		// 模块名称（关键字）
	private String moduleDescribe;		// 模块功能描述
	
	public Gaowh() {
		super();
	}

	public Gaowh(String id){
		super(id);
	}

	@Length(min=0, max=64, message="模块名称（关键字）长度必须介于 0 和 64 之间")
				@ExcelField(title="模块名称（关键字）", align=2, sort=7)
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}


	@Length(min=0, max=64, message="模块功能描述长度必须介于 0 和 64 之间")
				@ExcelField(title="模块功能描述", align=2, sort=8)
	public String getModuleDescribe() {
		return moduleDescribe;
	}

	public void setModuleDescribe(String moduleDescribe) {
		this.moduleDescribe = moduleDescribe;
	}


}