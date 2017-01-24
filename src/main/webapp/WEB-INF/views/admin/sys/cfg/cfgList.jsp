<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>系统配置项</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
	$("#toPage").val(1);
	$("#sf").submit();
	return false;
}
function deleteMenu(){
	if($("input[name='cfgId']:checked").size()==0){
	  	$.messager.alert("消息", "请选择需要删除的配置项！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "您确定要删除该批配置项么?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/sys/cfg/deleteCfg.do",
				reqData:"cfgId="+$("input[name='cfgId']:checked").val(),
				dataType: "html",
				successfn: function(data){
					if(data =='"SUCCESS"'){
				        $.messager.alert("消息", "删除成功!", "info",function (){
				         window.location.href="${base}/admin/sys/cfg/cfgList.do";
				        });
				    }else{
				    	$.messager.alert("消息", "删除失败!", "error");
				    }
				}
			});
	    }
	});
}
$(document).ready(function (){
	$("#sf").validate({submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit()}});
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/sys/cfg/cfgList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="4">
		<tr>
			 <td class="Search_Label">配置项名称:</td>
			 <td class="Search_Field"><input type="text" name="cfgName" value="${cfg.cfgName}" class="NoneBorderTextBox" /></td>
			 <td class="Search_Label">配置项Key:</td>
			 <td class="Search_Field"><input type="text" name="cfgKey" value="${cfg.cfgKey}" class="NoneBorderTextBox"/></td>
		</tr>
		<tr>
			<td colspan="4" class="Search_BtnRow" >
			<a class="easyui-linkbutton" iconCls="icon-cancel"  href="javascript:deleteMenu()"  style="width: 70px">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-edit"  href="javascript:parent.addTab('系统配置项编辑','${base}/admin/sys/cfg/cfgDisplay.do')" style="width: 70px">新增</a>
			<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:searchForm()"  style="width: 70px">搜索</a>
			</td>
		</tr>
	</table>
		<input type="hidden" name="toPage" value="${pagin.toPage}" id="toPage" />
		<input type="hidden" name="pageSize" value="${pagin.pageSize}" id="pageSize" />
</form>
	<hr title="点南隐藏搜索框" class="searchTabSplit">
	<table class="ListTable" cellspacing="1" cellpadding="4">
		<tr class="ListTableHeader">
			<td>ID</td>
			<th>配置项名称</th>
			<th>配置项Key</th>
			<th>配置项Value</th>
			<th>配置项Desc</th>
			<th>排序</th>
			<th width="70">操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td><input type="checkbox"  name="cfgId" value="${item.cfgId}"/>${item.cfgId }</td>
				<td>${item.cfgName}</td>
				<td>${item.cfgKey}</td>
				<td>${item.cfgValue}</td>
				<td>${item.cfgDesc}</td>
				<td>${item.cfgSn}</td>
				<td class="actionBar"><a href="javascript:parent.addTab('系统配置项编辑','${base}/admin/sys/cfg/cfgDisplay.do?cfgId=${item.cfgId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" >编辑</a></td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="7" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
</html>