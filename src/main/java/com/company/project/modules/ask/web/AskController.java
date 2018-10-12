/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.ask.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.jeespring.common.utils.DateUtils;
import com.jeespring.common.config.Global;
import com.jeespring.common.persistence.Page;
import com.jeespring.common.web.AbstractBaseController;
import com.jeespring.common.utils.StringUtils;
import com.jeespring.common.utils.excel.ExportExcel;
import com.jeespring.common.utils.excel.ImportExcel;
import com.company.project.modules.ask.entity.Ask;
import com.company.project.modules.ask.service.AskService;
/**
 * 问卷调查描述Controller
 * @author gaowenhui
 * @version 2018-10-12
 */
@Controller
@RequestMapping(value = "${adminPath}/ask/ask")
public class AskController extends AbstractBaseController {

	@Autowired
	private AskService askService;
	
	@ModelAttribute
	public Ask get(@RequestParam(required=false) String id) {
		Ask entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = askService.getCache(id);
			//entity = askService.get(id);
		}
		if (entity == null){
			entity = new Ask();
		}
		return entity;
	}

	/**
	 * 问卷调查功能统计页面
	 */
	@RequiresPermissions("ask:ask:total")
	@RequestMapping(value = {"total"})
	public String totalView(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(ask,request,response,model);
		return "modules/ask/askTotal";
	}
	private void total(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(ask.getTotalType())){
			ask.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(ask.getOrderBy()==""){
			ask.setOrderBy("totalDate");
		}
		List<Ask> list = askService.totalCache(ask);
		//List<Ask> list = askService.total(ask);
		model.addAttribute("list", list);
		for(Ask askItem:list){
			//x轴数据
			xAxisData.add( askItem.getTotalDate());
			countList.add(Double.valueOf(askItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(Ask::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(Ask askItem:list){
			orientData.put(askItem.getTotalDate(), askItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("ask:ask:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(ask.getTotalType())){
			ask.setTotalType("%Y-%m-%d");
		}
		List<Ask> list = askService.totalCache(ask);
		//List<Ask> list = askService.total(ask);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(Ask::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/ask/askTotalMap";
	}

	/**
	 * 问卷调查功能列表页面
	 */
	@RequiresPermissions("ask:ask:list")
	@RequestMapping(value = {"list", ""})
	public String list(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Ask> page = askService.findPageCache(new Page<Ask>(request, response), ask);
		//Page<Ask> page = askService.findPage(new Page<Ask>(request, response), ask);
		model.addAttribute("page", page);
		ask.setOrderBy("totalDate");
		total(ask,request,response,model);
		return "modules/ask/askList";
	}

	/**
	 * 问卷调查功能列表页面
	 */
	@RequiresPermissions("ask:ask:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Ask> page = askService.findPageCache(new Page<Ask>(request, response), ask);
		//Page<Ask> page = askService.findPage(new Page<Ask>(request, response), ask);
		model.addAttribute("page", page);
		return "modules/ask/askListVue";
	}

	/**
	 * 问卷调查功能列表页面
	 */
	//RequiresPermissions("ask:ask:select")
	@RequestMapping(value = {"select"})
	public String select(Ask ask, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Ask> page = askService.findPageCache(new Page<Ask>(request, response), ask);
		//Page<Ask> page = askService.findPage(new Page<Ask>(request, response), ask);
		model.addAttribute("page", page);
		return "modules/ask/askSelect";
	}

	/**
	 * 查看，增加，编辑问卷调查功能表单页面
	 */
	@RequiresPermissions(value={"ask:ask:view","ask:ask:add","ask:ask:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Ask ask, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("ask", ask);
		if(request.getParameter("ViewFormType")!=null && request.getParameter("ViewFormType").equals("FormTwo"))
			return "modules/ask/askFormTwo";
		return "modules/ask/askForm";
	}

	/**
	 * 保存问卷调查功能
	 */
	@RequiresPermissions(value={"ask:ask:add","ask:ask:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Ask ask, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, ask)){
			return form(ask, model,request,response);
		}
		askService.save(ask);
		addMessage(redirectAttributes, "保存问卷调查功能成功");
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
	}

	/**
	 * 删除问卷调查功能
	 */
	@RequiresPermissions("ask:ask:del")
	@RequestMapping(value = "delete")
	public String delete(Ask ask, RedirectAttributes redirectAttributes) {
		askService.delete(ask);
		addMessage(redirectAttributes, "删除问卷调查功能成功");
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
	}

	/**
	 * 删除问卷调查功能（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"ask:ask:del","ask:ask:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(Ask ask, RedirectAttributes redirectAttributes) {
		askService.deleteByLogic(ask);
		addMessage(redirectAttributes, "逻辑删除问卷调查功能成功");
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
	}

	/**
	 * 批量删除问卷调查功能
	 */
	@RequiresPermissions("ask:ask:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			askService.delete(askService.get(id));
		}
		addMessage(redirectAttributes, "删除问卷调查功能成功");
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
	}

	/**
	 * 批量删除问卷调查功能（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"ask:ask:del","ask:ask:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			askService.deleteByLogic(askService.get(id));
		}
		addMessage(redirectAttributes, "删除问卷调查功能成功");
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("ask:ask:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Ask ask, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷调查功能"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Ask> page = askService.findPage(new Page<Ask>(request, response, -1), ask);
    		new ExportExcel("问卷调查功能", Ask.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出问卷调查功能记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("ask:ask:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Ask> list = ei.getDataList(Ask.class);
			for (Ask ask : list){
				askService.save(ask);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条问卷调查功能记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入问卷调查功能失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
    }
	
	/**
	 * 下载导入问卷调查功能数据模板
	 */
	@RequiresPermissions("ask:ask:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "问卷调查功能数据导入模板.xlsx";
    		List<Ask> list = Lists.newArrayList(); 
    		new ExportExcel("问卷调查功能数据", Ask.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/ask/ask/?repage";
    }
	

}