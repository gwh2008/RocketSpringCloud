<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问卷调查功能管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<%@ include file="/WEB-INF/views/include/echarts.jsp"%>
	<script src="/static/views/modules/ask//askList.js" type="text/javascript"></script>
	<link href="/static/views/modules/ask//askList.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content content-background">
	<div class="ibox">
    <div class="ibox-content content">
	<sys:message content="${message}"/>
	
	<!--查询条件-->
	<div class="row">
	<div class="col-sm-12">
	<form:form id="searchForm" modelAttribute="ask" action="${ctx}/ask/ask/" method="post" class="form-inline">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
		<div class="form-group">
			<span>问卷选项：</span>
				<form:input path="askItem" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
		 </div>	
	</form:form>
	<br/>
	</div>
	</div>
	
	<!-- 工具栏 -->
	<div class="row">
	<div class="col-sm-12">
		<div class="pull-left">
			<button  class="btn btn-success btn-sm " onclick="$('#searchForm').toggle();$('.fa-chevron').toggle();"  title="检索">
				<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 检索
			</button>
			<button  class="btn btn-success btn-sm " onclick="$('#total').toggle();$('.fa-chevron').toggle();"  title="统计">
				<i class="fa-chevron fa fa-chevron-up"></i><i class="fa-chevron fa fa-chevron-down" style="display:none"></i> 统计
			</button>
			<button  class="btn btn-success btn-sm " onclick="search()" title="查询"><i class="fa fa-search"></i> 查询</button>
			<shiro:hasPermission name="ask:ask:add">
				<table:addRow url="${ctx}/ask/ask/form" title="问卷调查功能"></table:addRow><!-- 增加按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ask:ask:edit">
			    <table:editRow url="${ctx}/ask/ask/form" title="问卷调查功能" id="contentTable"></table:editRow><!-- 编辑按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ask:ask:del">
				<table:delRow url="${ctx}/ask/ask/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ask:ask:import">
				<table:importExcel url="${ctx}/ask/ask/import"></table:importExcel><!-- 导入按钮 -->
			</shiro:hasPermission>
			<shiro:hasPermission name="ask:ask:export">
	       		<table:exportExcel url="${ctx}/ask/ask/export"></table:exportExcel><!-- 导出按钮 -->
	       	</shiro:hasPermission>
	        <button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
	       	<button  class="btn btn-white btn-sm " onclick="reset()"  title="重置"><i class="fa fa-refresh"></i> 重置</button>
		</div>
		<div class="pull-right">
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/ask/ask/listVue','Vue问卷调查功能', false)" title="Vue.js"><i class="glyphicon glyphicon-repeat"></i> Vue.js</button>
			<shiro:hasPermission name="ask:ask:total">
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/ask/ask/total','统计问卷调查功能', false)" title="统计图表"><i class="glyphicon glyphicon-repeat"></i> 统计图表</button>
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/ask/ask/totalMap','统计问卷调查功能', false)" title="统计地图"><i class="glyphicon glyphicon-repeat"></i> 统计地图</button>
			</shiro:hasPermission>
			<button class="btn btn-success " type="button" name="toggle" title="切换" onclick="$('.table').toggle()"><i class="glyphicon glyphicon-list-alt icon-list-alt"></i></button>
			<div class="btn-group" title="其他">
				<button class="btn btn-success btn-sm dropdown-toggle" data-toggle="dropdown" type="button" aria-expanded="false">
					<i class="glyphicon glyphicon-th icon-th"></i>
					<span class="caret"></span>
				</button>
				<ul class="dropdown-menu" role="menu">
					<li data-type="放大"><a href="javascript:void(0)" onclick="$('body').css({zoom:Number($('body').css('zoom'))+0.1})">放大</a></li>
					<li data-type="缩小"><a href="javascript:void(0)" onclick="$('body').css({zoom:$('body').css('zoom')-0.1})">缩小</a></li>
				</ul>
			</div>
		</div>
	</div>
	</div>

	<!-- 表格 -->
	<div class="table" style="display:none">
		<c:forEach items="${page.list}" var="ask">
			<div style="border: 1px solid #e7eaec;padding: 8px;" class="row">
				<div>
				<input type="checkbox" id="${ask.id}"
					remarks="${ask.remarks}"
					askItem="${ask.askItem}"
				class="i-checks">
				</div>
				<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
				备注信息:
				<a  href="#" onclick="openDialogView('查看问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}','800px', '500px')">
					${ask.remarks}
				</a></span>
						</div>
				<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
				问卷选项:
				
					${ask.askItem}
				</span>
						</div>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<shiro:hasPermission name="ask:ask:view">
						<a href="#" onclick="openDialogView('查看问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}&action=view','800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i>查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ask:ask:edit">
    					<a href="#" onclick="openDialog('修改问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}','800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i>修改</a>
    				</shiro:hasPermission>
					<shiro:hasPermission name="ask:ask:edit">
    					<a href="#" onclick="top.openTab('${ctx}/ask/ask/form?id=${ask.id}','修改问卷调查功能', false)" title="修改" class="btn btn-success btn-sm" title="修改(页签)"><i class="fa fa-edit"></i>修改(页签)</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ask:ask:edit">
    					<a href="#" onclick="top.openTab('${ctx}/ask/ask/form?id=${ask.id}&ViewFormType=FormTwo','修改问卷调查功能', false)" title="修改2" class="btn btn-success btn-sm" title="修改(页签)2"><i class="fa fa-edit"></i>修改(页签)2</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ask:ask:del">
						<a href="${ctx}/ask/ask/delete?id=${ask.id}" onclick="return confirmx('确认要删除该问卷调查功能吗？', this.href)"   class="btn btn-danger btn-sm"  title="删除"><i class="fa fa-trash"></i>删除</a>
					</shiro:hasPermission>
				</div>
			</div>
		</c:forEach>
	</div>

	<!-- 表格 -->
	<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
		<thead>
			<tr>
				<th> <input type="checkbox" class="i-checks"></th>
				<th  class="sort-column remarks hidden-xs">备注信息</th>
				<th  class="sort-column askItem hidden-xs">问卷选项</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ask">
			<tr>
				<td>
				<input type="checkbox" id="${ask.id}"
					remarks="${ask.remarks}"
					askItem="${ask.askItem}"
				class="i-checks"></td>
				<td class="hidden-xs"><a  href="#" onclick="openDialogView('查看问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}&action=view','800px', '500px')">
					${ask.remarks}
				</a></td>
				<td class="hidden-xs">
					${ask.askItem}
				</td>
				<td>
					<shiro:hasPermission name="ask:ask:view">
						<a href="#" onclick="openDialogView('查看问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}&action=view','800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i>查看</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="ask:ask:edit">
    					<!--a href="#" onclick="openDialog('修改问卷调查功能', '${ctx}/ask/ask/form?id=${ask.id}','800px', '500px',null,true)" class="btn btn-success btn-sm" title="修改(弹窗)"><i class="fa fa-edit"></i>修改(弹窗)</a-->
    				</shiro:hasPermission>
    				 <shiro:hasPermission name="ask:ask:edit">
    					<a href="${ctx}/ask/ask/form?id=${ask.id}" title="修改" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i>修改</a>
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ask:ask:edit">
    					<!--a href="#" onclick="top.openTab('${ctx}/ask/ask/form?id=${ask.id}','修改问卷调查功能', false)" title="修改" class="btn btn-success btn-sm" title="修改(页签)"><i class="fa fa-edit"></i>修改(页签)</a-->
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ask:ask:edit">
    					<!--a href="#" onclick="top.openTab('${ctx}/ask/ask/form?id=${ask.id}&ViewFormType=FormTwo','修改问卷调查功能', false)" title="修改2" class="btn btn-success btn-sm" title="修改(页签)2"><i class="fa fa-edit"></i>修改(页签)2</a-->
    				</shiro:hasPermission>
    				<shiro:hasPermission name="ask:ask:del">
						<a href="${ctx}/ask/ask/delete?id=${ask.id}" onclick="return confirmx('确认要删除该问卷调查功能吗？', this.href)"   class="btn btn-danger btn-sm" title="删除"><i class="fa fa-trash"></i>删除</a>
					</shiro:hasPermission>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<!-- 分页代码 -->
	<table:page page="${page}"></table:page>
	<br/>
		<!-- 统计 -->
	<div class="row" id="total" style="margin-top: 10px;">
		<div class="col-sm-12 echartsEval">
			<h4>合计：${sumTotalCount}行;
			</h4>
			<div id="pie"  class="main000"></div>
			<echarts:pie
					id="pie"
					title="问卷调查功能数量饼图"
					subtitle="问卷调查功能数量饼图"
					orientData="${orientData}"/>

			<div id="line_normal"  class="main000"></div>
			<echarts:line
			id="line_normal"
			title="问卷调查功能曲线"
			subtitle="问卷调查功能曲线"
			xAxisData="${xAxisData}"
			yAxisData="${yAxisData}"
			xAxisName="时间"
			yAxisName="数量" />
		</div>
	</div>

	</div>
	</div>
</div>
</body>
</html>