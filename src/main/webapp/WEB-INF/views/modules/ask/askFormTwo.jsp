<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>问卷调查功能管理</title>
	<meta name="decorator" content="default"/>
	<%@ include file="/WEB-INF/views/include/head.jsp"%>
	<script src="/static/views/modules/ask//askForm.js" type="text/javascript"></script>
	<link href="/static/views/modules/ask//askForm.css" rel="stylesheet" />
</head>
<body class="gray-bg">
	<form:form id="inputForm" modelAttribute="ask" action="${ctx}/ask/ask/save" method="post" class="form-horizontal  content-background">
		<div class="content">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		<table class="width-100 table-condensed dataTables-example dataTable no-footer">
		   <tbody>
				<tr>
					<td></td></tr><tr>
					<td class="width-15 active" valign="top"><label class="pull-left">备注信息：</label></td>
					<td class="width-35" colspan="3">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="form-control "/>
						</td>
					</tr>
				<tr>
					<td class="width-15 active" valign="top"><label class="pull-left">问卷选项：</label></td>
					<td class="width-35">
							<form:input path="askItem" htmlEscape="false" maxlength="64" class="form-control "/>
						<div class="help-block">请填写问卷选项</div>
						</td>
			</tbody>
		</table>
			<div id="iframeSave" class="form-group  ${action}">
				<a class="btn btn-success" onclick="doSubmit();">保存</a>
				<a class="btn btn-primary" onclick="javascript:history.back(-1);">返回</a>
				<!--a class="btn btn-primary" onclick="top.closeSelectTabs()">关闭</a-->
			</div>
		</div>
	</form:form>
</body>
</html>