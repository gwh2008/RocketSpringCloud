/** * * Copyright &copy; 2015-2020 <a href="https://github.com/gaowenhui/RocketSpringCloud">JeeSpring</a> All rights reserved.. */
package com.company.project.modules.gaowh.web;

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
import com.company.project.modules.gaowh.entity.Gaowh;
import com.company.project.modules.gaowh.service.GaowhService;
/**
 * 模块管理（上传和下载）Controller
 * @author gaowenhui
 * @version 2018-11-21
 */
@Controller
@RequestMapping(value = "${adminPath}/gaowh/gaowh")
public class GaowhController extends AbstractBaseController {

	@Autowired
	private GaowhService gaowhService;
	
	@ModelAttribute
	public Gaowh get(@RequestParam(required=false) String id) {
		Gaowh entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = gaowhService.getCache(id);
			//entity = gaowhService.get(id);
		}
		if (entity == null){
			entity = new Gaowh();
		}
		return entity;
	}

	/**
	 * 模块管理统计页面
	 */
	@RequiresPermissions("gaowh:gaowh:total")
	@RequestMapping(value = {"total"})
	public String totalView(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
		total(gaowh,request,response,model);
		return "modules/gaowh/gaowhTotal";
	}
	private void total(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
			if(StringUtils.isEmpty(gaowh.getTotalType())){
			gaowh.setTotalType("%Y-%m-%d");
		}
		//X轴的数据
		List<String> xAxisData= new ArrayList<String>();
		//Y轴的数据
		Map<String,List<Double>> yAxisData = new HashMap<String,List<Double>>();
		List<Double> countList = new ArrayList<Double>();
		List<Double> sumList = new ArrayList<Double>();
		if(gaowh.getOrderBy()==""){
			gaowh.setOrderBy("totalDate");
		}
		List<Gaowh> list = gaowhService.totalCache(gaowh);
		//List<Gaowh> list = gaowhService.total(gaowh);
		model.addAttribute("list", list);
		for(Gaowh gaowhItem:list){
			//x轴数据
			xAxisData.add( gaowhItem.getTotalDate());
			countList.add(Double.valueOf(gaowhItem.getTotalCount()));
		}
		yAxisData.put("数量", countList);
	    request.setAttribute("xAxisData", xAxisData);
		request.setAttribute("yAxisData", yAxisData);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(Gaowh::getTotalCount).sum());

		//饼图数据
		Map<String,Object> orientData= new HashMap<String,Object>();
		for(Gaowh gaowhItem:list){
			orientData.put(gaowhItem.getTotalDate(), gaowhItem.getTotalCount());
		}
		model.addAttribute("orientData", orientData);
	}
	@RequiresPermissions("gaowh:gaowh:total")
	@RequestMapping(value = {"totalMap"})
	public String totalMap(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(StringUtils.isEmpty(gaowh.getTotalType())){
			gaowh.setTotalType("%Y-%m-%d");
		}
		List<Gaowh> list = gaowhService.totalCache(gaowh);
		//List<Gaowh> list = gaowhService.total(gaowh);
		model.addAttribute("sumTotalCount", list.stream().mapToInt(Gaowh::getTotalCount).sum());
		model.addAttribute("list", list);
		return "modules/gaowh/gaowhTotalMap";
	}

	/**
	 * 模块管理列表页面
	 */
	@RequiresPermissions("gaowh:gaowh:list")
	@RequestMapping(value = {"list", ""})
	public String list(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gaowh> page = gaowhService.findPageCache(new Page<Gaowh>(request, response), gaowh);
		//Page<Gaowh> page = gaowhService.findPage(new Page<Gaowh>(request, response), gaowh);
		model.addAttribute("page", page);
		gaowh.setOrderBy("totalDate");
		total(gaowh,request,response,model);
		return "modules/gaowh/gaowhList";
	}

	/**
	 * 模块管理列表页面
	 */
	@RequiresPermissions("gaowh:gaowh:list")
	@RequestMapping(value = {"listVue"})
	public String listVue(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gaowh> page = gaowhService.findPageCache(new Page<Gaowh>(request, response), gaowh);
		//Page<Gaowh> page = gaowhService.findPage(new Page<Gaowh>(request, response), gaowh);
		model.addAttribute("page", page);
		return "modules/gaowh/gaowhListVue";
	}

	/**
	 * 模块管理列表页面
	 */
	//RequiresPermissions("gaowh:gaowh:select")
	@RequestMapping(value = {"select"})
	public String select(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Gaowh> page = gaowhService.findPageCache(new Page<Gaowh>(request, response), gaowh);
		//Page<Gaowh> page = gaowhService.findPage(new Page<Gaowh>(request, response), gaowh);
		model.addAttribute("page", page);
		return "modules/gaowh/gaowhSelect";
	}

	/**
	 * 查看，增加，编辑模块管理表单页面
	 */
	@RequiresPermissions(value={"gaowh:gaowh:view","gaowh:gaowh:add","gaowh:gaowh:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Gaowh gaowh, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("action", request.getParameter("action"));
		model.addAttribute("gaowh", gaowh);
		if(request.getParameter("ViewFormType")!=null && request.getParameter("ViewFormType").equals("FormTwo"))
			return "modules/gaowh/gaowhFormTwo";
		return "modules/gaowh/gaowhForm";
	}

	/**
	 * 保存模块管理
	 */
	@RequiresPermissions(value={"gaowh:gaowh:add","gaowh:gaowh:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(Gaowh gaowh, Model model, RedirectAttributes redirectAttributes, HttpServletRequest request, HttpServletResponse response) {
		if (!beanValidator(model, gaowh)){
			return form(gaowh, model,request,response);
		}
		gaowhService.save(gaowh);
		addMessage(redirectAttributes, "保存模块管理成功");
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
	}

	/**
	 * 删除模块管理
	 */
	@RequiresPermissions("gaowh:gaowh:del")
	@RequestMapping(value = "delete")
	public String delete(Gaowh gaowh, RedirectAttributes redirectAttributes) {
		gaowhService.delete(gaowh);
		addMessage(redirectAttributes, "删除模块管理成功");
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
	}

	/**
	 * 删除模块管理（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"gaowh:gaowh:del","gaowh:gaowh:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteByLogic")
	public String deleteByLogic(Gaowh gaowh, RedirectAttributes redirectAttributes) {
		gaowhService.deleteByLogic(gaowh);
		addMessage(redirectAttributes, "逻辑删除模块管理成功");
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
	}

	/**
	 * 批量删除模块管理
	 */
	@RequiresPermissions("gaowh:gaowh:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			gaowhService.delete(gaowhService.get(id));
		}
		addMessage(redirectAttributes, "删除模块管理成功");
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
	}

	/**
	 * 批量删除模块管理（逻辑删除，更新del_flag字段为1,在表包含字段del_flag时，可以调用此方法，将数据隐藏）
	 */
	@RequiresPermissions(value={"gaowh:gaowh:del","gaowh:gaowh:delByLogic"},logical=Logical.OR)
	@RequestMapping(value = "deleteAllByLogic")
	public String deleteAllByLogic(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			gaowhService.deleteByLogic(gaowhService.get(id));
		}
		addMessage(redirectAttributes, "删除模块管理成功");
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("gaowh:gaowh:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(Gaowh gaowh, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Gaowh> page = gaowhService.findPage(new Page<Gaowh>(request, response, -1), gaowh);
    		new ExportExcel("模块管理", Gaowh.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出模块管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("gaowh:gaowh:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Gaowh> list = ei.getDataList(Gaowh.class);
			for (Gaowh gaowh : list){
				gaowhService.save(gaowh);
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条模块管理记录");
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模块管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
    }
	
	/**
	 * 下载导入模块管理数据模板
	 */
	@RequiresPermissions("gaowh:gaowh:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "模块管理数据导入模板.xlsx";
    		List<Gaowh> list = Lists.newArrayList(); 
    		new ExportExcel("模块管理数据", Gaowh.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/gaowh/gaowh/?repage";
    }
	

}