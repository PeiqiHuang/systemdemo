<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="myTag" uri="/WEB-INF/myTag.tld" %>
<!DOCTYPE HTML>
<html>
<head>
<title>用户查询列表</title>
<%@include file="/WEB-INF/views/admin/css_Script.jsp" %>
<script type="text/javascript">
function searchForm(){
$("#toPage").val(1);
$("#sf").submit();
return false;
}

$(document).ready(function (){
	$("#sf").validate({ submitHandler:function(form){var win = $.messager.progress({title:'请等待...',text:''});form.submit();} });
});
</script>
</head>
<body>
<div class="ContentContainer">
<form action="${base}/admin/user/userinfo/userInfoList.do" method="post" id="sf">
	<table class="SearchTable" cellspacing="1" cellpadding="4">
		<tr>
			 <td class="Search_Label">用户ID:</td>
			 <td class="Search_Field"><input type="text" name="userId" value="${user.userId}" class="NoneBorderTextBox" /></td>
			 <td class="Search_Label">用户手机号:</td>
			 <td class="Search_Field"><input type="text" name="mobilePhone" value="${user.mobilePhone}" class="NoneBorderTextBox" /></td>
		</tr>
		<tr>
			<td colspan="4" class="Search_BtnRow" >
				<a class="easyui-linkbutton" iconCls="icon-search" href="javascript:searchForm()"  style="width: 80px">搜索</a>
			</td>
		</tr>
	</table>
		<input type="hidden" name="toPage" value="${pagin.toPage}" id="toPage" />
		<input type="hidden" name="pageSize" value="${pagin.pageSize}" id="pageSize" />
</form>
	<hr title="点南隐藏搜索框" class="searchTabSplit">
	<table class="ListTable" cellspacing="1" cellpadding="4">
		<tr class="ListTableHeader">
			<th>用户ID</th>
			<th>用户姓名</th>
			<th>用户手机号</th>
			<th>状态</th>
			<th>创建时间</th>
			<th>操作</th>
		</tr>
		<tbody class="ListTableContent">
		    <c:forEach items="${pagin.resultLists}" var="item" varStatus="vs">
			<tr class="${vs.count%2 ==0 ? 'ListTableOddRow':'ListTableEvenRow'}">
				<td><input type="radio"  name="resetSuId" value="${item.userId}"/>${item.userId }</td>
				<td>${item.userName}</td>
				<td>${item.mobilePhone}</td>
				<td>${item.userStatus eq 1 ? "正常":"<font color='red'>禁用</font>"}</td>
				<td><fmt:formatDate value="${item.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td class="actionBar">
					<a href="javascript:parent.addTab('用户信息','${base}/admin/user/userinfo/userInfoDisplay.do?userId=${item.userId}')" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-edit'" >查看</a>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		<myTag:paginTag colspan="7" pagination="${pagin}" pageFormId="sf"/>
	</table>
</div>
</body>
</html>