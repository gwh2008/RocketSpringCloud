<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>模块管理管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/static/views/modules/gaowh//gaowhList.js" type="text/javascript"></script>
	<link href="/static/views/modules/gaowh//gaowhList.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<div class="wrapper wrapper-content"  id="rrapp">
		<div class="ibox">
		<!--div class="ibox-title">
			<h5>模块管理列表 </h5>
		</div-->

		<div class="ibox-content">
		<sys:message content="${message}"/>

		<!--查询条件-->
		<div class="row">
		<div class="col-sm-12">
		<form:form id="searchForm" modelAttribute="gaowh" action="${ctx}/../rest/gaowh/gaowh/list" method="post" class="form-inline">
			<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
			<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
			<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
			<div class="form-group">
				<span>模块名称（关键字）：</span>
					<form:input path="moduleName" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
				<span>模块功能描述：</span>
					<form:input path="moduleDescribe" htmlEscape="false" maxlength="64"  class=" form-control input-sm"/>
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
				<button  class="btn btn-success btn-sm " @click="search()" title="查询"><i class="fa fa-search"></i> 查询</button>
				<shiro:hasPermission name="gaowh:gaowh:add">
					<table:addRow url="${ctx}/gaowh/gaowh/form" title="模块管理"></table:addRow><!-- 增加按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="gaowh:gaowh:edit">
					<table:editRow url="${ctx}/gaowh/gaowh/form" title="模块管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="gaowh:gaowh:del">
					<table:delRow url="${ctx}/gaowh/gaowh/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="gaowh:gaowh:import">
					<table:importExcel url="${ctx}/gaowh/gaowh/import"></table:importExcel><!-- 导入按钮 -->
				</shiro:hasPermission>
				<shiro:hasPermission name="gaowh:gaowh:export">
					<table:exportExcel url="${ctx}/gaowh/gaowh/export"></table:exportExcel><!-- 导出按钮 -->
				</shiro:hasPermission>
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="sortOrRefresh()" title="刷新"><i class="glyphicon glyphicon-repeat"></i> 刷新</button>
				<button  class="btn btn-white btn-sm " onclick="reset()"  title="重置"><i class="fa fa-refresh"></i> 重置</button>
			</div>
			<div class="pull-right">
				<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/gaowh/gaowh','模块管理', false)" title="list"><i class="glyphicon glyphicon-repeat"></i> list</button>
				<shiro:hasPermission name="gaowh:gaowh:total">
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/gaowh/gaowh/total','统计模块管理', false)" title="统计图表"><i class="glyphicon glyphicon-repeat"></i> 统计图表</button>
			<button class="btn btn-white btn-sm " data-toggle="tooltip" data-placement="left" onclick="top.openTab('${ctx}/gaowh/gaowh/totalMap','统计模块管理', false)" title="统计地图"><i class="glyphicon glyphicon-repeat"></i> 统计地图</button>
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
				<div style="border: 1px solid #e7eaec;padding: 8px;" class="row" v-for="item in page">
					<div>
					<input type="checkbox" :id="item.id"
						moduleName="${gaowh.moduleName}"
						moduleDescribe="${gaowh.moduleDescribe}"
					class="i-checks">
					</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					模块名称（关键字）:
					<a  href="#" v-on:onclick="openDialogView('查看模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')">
						{{item.moduleName}}
					</a></span>
							</div>
					<div class="col-xs-6 col-sm-4 col-md-3 col-lg-2">
					模块功能描述:
					
						{{item.moduleDescribe}}
					</span>
							</div>
					<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
						<shiro:hasPermission name="gaowh:gaowh:view">
							<a href="#" onclick="openDialogView('查看模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> 查看</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="gaowh:gaowh:edit">
							<a href="#" onclick="openDialog('修改模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> 修改</a>
						</shiro:hasPermission>
						<shiro:hasPermission name="gaowh:gaowh:del">
							<a href="${ctx}/gaowh/gaowh/delete?id='+item.id" onclick="return confirmx('确认要删除该模块管理吗？', this.href)"   class="btn btn-danger btn-sm"  title="删除"><i class="fa fa-trash"></i> 删除</a>
						</shiro:hasPermission>
					</div>
				</div>
		</div>

		<!-- 表格 -->
		<table id="contentTable" class="table table-hover table-condensed dataTables-example dataTable">
			<thead>
				<tr>
					<th> <input type="checkbox" class="i-checks"></th>
					<th  class="sort-column moduleName hidden-xs">模块名称（关键字）</th>
					<th  class="sort-column moduleDescribe hidden-xs">模块功能描述</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr  v-for="item in page" >
					<td>
					<input type="checkbox" :id="item.id"
						moduleName="${gaowh.moduleName}"
						moduleDescribe="${gaowh.moduleDescribe}"
					class="i-checks"></td>
					<td  class="hidden-xs"><a  href="#" v-on:click="openDialogView('查看模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')">
					{{item.moduleName}}
					</a></td>
					<td  class="hidden-xs">
					{{item.moduleDescribe}}
					</td>
					<td>
						<shiro:hasPermission name="gaowh:gaowh:view">
							<a href="#" v-on:click="openDialogView('查看模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')" class="btn btn-info btn-sm" title="查看"><i class="fa fa-search-plus"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="gaowh:gaowh:edit">
							<a href="#" v-on:click="openDialog('修改模块管理', '${ctx}/gaowh/gaowh/form?id='+item.id,'800px', '500px')" class="btn btn-success btn-sm" title="修改"><i class="fa fa-edit"></i> </a>
						</shiro:hasPermission>
						<shiro:hasPermission name="gaowh:gaowh:edit">
    						<a href="#" v-on:click="top.openTab('${ctx}/gaowh/gaowh/form?id='+item.id,'修改模块管理', false)" title="修改" class="btn btn-success btn-sm" title=" 修改(页签)"><i class="fa fa-edit"></i></a>
    					</shiro:hasPermission>
						<shiro:hasPermission name="gaowh:gaowh:del">
							<a v-bind:href="'${ctx}/gaowh/gaowh/delete?id='+item.id" onclick="return confirmx('确认要删除该模块管理吗？', this.href)"   class="btn btn-danger btn-sm" title="删除"><i class="fa fa-trash"></i> </a>
						</shiro:hasPermission>
					</td>
				</tr>
			</tbody>
		</table>
			<!-- 分页代码 -->
			<div v-html="result.html">
				{{result.html}}
			</div>
		<br/>
		<br/>
		</div>
		</div>
	</div>
	<script src="/static/vue/vue.min.js"></script>
	<script src="/static/common/SpringUI.js"></script>
</body>
</html>