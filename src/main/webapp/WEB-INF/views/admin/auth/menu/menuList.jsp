<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>系统权限菜单</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
	$("#toPage").val(1);
	$("#sf").submit();
	return false;
}
function deleteMenu(){
	if($("input[name='delMenu']:checked").size()==0){
	  	$.messager.alert("消息", "请选择需要删除的菜单！", "error");
 		return false;
	}
	$.messager.confirm("操作提示", "您确定要删除该菜单权限么?", function (flag) {
	    if (flag) {
	         $.myAjax.excuteAjaxMask({
				url:"${base}/admin/auth/menu/deleteMenu.do",
				reqData:"menuId="+$("input[name='delMenu']:checked").val(),
				dataType: "text",
				successfn: function(data){
				     if(data == 0 ){
				        $.messager.alert("消息", "删除成功!", "info",function (){ window.location.href="${base}/admin/auth/menu/menuList.do";});
				     }else{
				        $.messager.alert("消息", "删除失败！该菜单拥有子菜单", "error");
				     }
				}
			});
	    }
	});
}
$(document).ready(function (){
	$("#menuType").val("${menu.menuType}");
	$("#sf").validate({  
	    rules: {"parentId": {number: true}},errorPlacement: function (error, element) {$(element).css("background-color","pink");},success: function (label, element) {$(element).css("background-color","");},
	    submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit()} 
	});
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/auth/menu/menuList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="4">
		<tr>
			 <td class="Search_Label">菜单名称:</td>
			 <td class="Search_Field"><input type="text" name="menuName" value="${menu.menuName}" class="NoneBorderTextBox" /></td>
			 
			<td class="Search_Label">父ID:</td>
			<td class="Search_Field"><input type="text" name="parentId" value="${menu.parentId}" class="NoneBorderTextBox" /></td>
			<td class="Search_Label">菜单类型:</td>
			<td class="Search_Field"> 
				<select name="menuType" id="menuType" class="DropDownList">
       				<option value="">全部</option><option value="1">系统级</option><option value="2">菜单级</option><option value="3">按纽级</option>
       		   </select>
       		</td>
		</tr>
		<tr>
			<td class="Search_Label">链接地址:</td>
			 <td class="Search_Field"><input type="text" name="linkAddress" value="${menu.linkAddress}" class="NoneBorderTextBox"/></td>
			<td colspan="4" class="Search_BtnRow" >
			<a class="easyui-linkbutton" iconCls="icon-cancel"  href="javascript:deleteMenu()"  style="width: 70px">删除</a>
			<a class="easyui-linkbutton" iconCls="icon-edit"  href="javascript:parent.addTab('菜单编辑','${base}/admin/auth/menu/menuDisplay.do')" style="width: 70px">新增</a>
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
			<th>链接地址</th>
			<th>菜单类型</th>
			<th>排序</th>
			<th>父菜单ID</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td><input type="radio"  name="delMenu" value="${item.menuId}"/>${item.menuId }</td>
				<td>${item.menuName}</td>
				<td>${item.linkAddress}</td>
				<td>
					<c:if test="${item.menuType eq 1}"><font color="#ff6666">系统级</font></c:if>
					<c:if test="${item.menuType eq 2}"><font>菜单级</font></c:if>
					<c:if test="${item.menuType eq 3}"><font color="#c1c1c1">按纽级</font></c:if>
				</td>
				<td>${item.sortNum}</td>
				<td>${item.parentId}</td>
				<td class="actionBar"><a href="javascript:parent.addTab('菜单编辑','${base}/admin/auth/menu/menuDisplay.do?menuId=${item.menuId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" >编辑</a></td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="8" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
</html>