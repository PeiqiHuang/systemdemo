<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>菜单项管理</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
	$("#toPage").val(1);
	$("#sf").submit();
	return false;
}
function reloadCache() {
	var url = "${base}/admin/auth/menuItem/reloadCache.do";
	$.post(url, function(data){
		if(data.code == "0") {
			$.messager.alert("消息", "更新缓存成功", "info");
		} else {
			$.messager.alert("错误", data.msg, "error");
		}
	}, "json");
}
$(document).ready(function (){
	$("#status").val("${menuItem.status}");
	$("#sf").validate({  
	    submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit();} 
	});
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/auth/menuItem/menuItemList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="2">
		<tr>
			 <td class="Search_Label">菜单名称:</td>
			 <td class="Search_Field"><input type="text" name="itemName" value="${menuItem.itemName}" class="NoneBorderTextBox" /></td>
			 
			<td class="Search_Label">状态:</td>
			<td class="Search_Field"> 
				<select name="status" id="status" class="DropDownList">
       				<option value="">全部</option>
       				<option value="5">默认</option>
       				<option value="0">普通</option>
       				<option value="9">锁定</option>       				
       				<option value="-1">屏蔽</option>
       		   </select>
       		</td>
		</tr>
		<tr>
			<td colspan="4" class="Search_BtnRow" >
			<a class="easyui-linkbutton" iconCls="icon-reload" href="javascript:reloadCache()" style="width: 110px">更新缓存</a>
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
			<td>菜单ID</td>
			<th>菜单名称</th>
			<!-- <th>展现位置</th> -->
			<th>排序</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td>${item.itemId }</td>
				<td>${item.itemName}</td>
				<%-- <td>${item.key}</td> --%>
				<td>${item.weight}</td>
				<td>
					<c:if test="${item.status eq 9}"><font>锁定</font></c:if>
					<c:if test="${item.status eq 5}"><font>默认</font></c:if>
					<c:if test="${item.status eq 0}"><font>普通</font></c:if>
					<c:if test="${item.status eq -1}"><font>屏蔽</font></c:if>
				</td>
				<td class="actionBar"><a href="javascript:parent.addTab('菜单项编辑','${base}/admin/auth/menuItem/menuItemDisplay.do?itemId=${item.itemId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" >编辑</a></td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="8" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
</html>