/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.ask.entity;

import org.hibernate.validator.constraints.Length;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jeespring.common.persistence.AbstractBaseEntity;
import com.jeespring.common.utils.excel.annotation.ExcelField;
import com.jeespring.modules.sys.utils.DictUtils;

/**
 * 问卷调查描述Entity
 * @author gaowenhui
 * @version 2018-10-12
 */
public class Ask extends AbstractBaseEntity<Ask> {
	
	private static final long serialVersionUID = 1L;
	private String askItem;		// 问卷选项
	
	public Ask() {
		super();
	}

	public Ask(String id){
		super(id);
	}

	@Length(min=0, max=64, message="问卷选项长度必须介于 0 和 64 之间")
				@ExcelField(title="问卷选项", align=2, sort=7)
	public String getAskItem() {
		return askItem;
	}

	public void setAskItem(String askItem) {
		this.askItem = askItem;
	}


}